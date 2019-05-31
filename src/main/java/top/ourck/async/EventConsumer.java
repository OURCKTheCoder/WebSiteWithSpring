package top.ourck.async;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;

import top.ourck.async.handler.EventHandler;
import top.ourck.dao.RedisDAO;
import top.ourck.util.RedisKeyUtil;

@Service
public class EventConsumer implements InitializingBean, ApplicationContextAware {

	private static Map<EventType, List<EventHandler>> HANDLER_MAPPING = new HashMap<EventType, List<EventHandler>>();
	private static ApplicationContext APPLICATION_CONTEXT;
	private static final Logger logger = LoggerFactory.getLogger("logger");
	
	@Autowired
	private RedisDAO redisDAO;
	
	private class EventMonitorThread implements Runnable {
		
		@Override
		public void run() {
			while(true) {
				String key = RedisKeyUtil.getEventQueueKey();
				List<String> eventStrs = redisDAO.brpop(0, key);
				//System.out.println(eventStrs);
				
				try {
					for(String str : eventStrs) {
						// The first element is the name of event queue in redis.
						if(str.equals(key)) continue;
						EventModel e = JSON.parseObject(str, EventModel.class);
						
						List<EventHandler> chain = HANDLER_MAPPING.get(e.getType());
						if(chain == null) {
							throw new Exception("Unsupported EventType: " + e.getType() + "!");
						}
						for(EventHandler handler : chain) {
							handler.doHandle(e);
						}
					}
				} catch(JSONException e) {
					logger.error(e.getMessage());
					// TODO 要不要保证原子性把这个东西push回去？
				} catch(Exception e) {
					logger.error(e.getMessage());
					// TODO 要不要保证原子性把这个东西push回去？
				}
			}
		}
	}

	// 一定要依赖Spring框架的生命周期吗？对的
	// 能不能直接放在构造块中？不行。
	// 
	// 因为初始化需要用到APPLICATION_CONTEXT，来取得作为@Component的各种EventHandler。
	// 而这玩意（似乎）只能通过重写setApplicationContext()来取得。
	// 换句话说，这玩意只能在Spring加载的特定生命周期过后获得。这也是第一个问题的答案。
	// 
	// 关于第二个问题，有了上面这段话就清楚了。
	// 因为构造器被调用时，是Spring新建Bean加载上下文的时候。
	// 而这个时候，APPLICATION_CONTEXT并没有被完全加载好，
	// 并且本来也没法直接通过构造器取得APPLICATION_CONTEXT。
	@Override
	public void afterPropertiesSet() throws Exception {
		// 1. Wiring routing table.
		Map<String, EventHandler> handlers = 
				APPLICATION_CONTEXT.getBeansOfType(EventHandler.class);
		for(Map.Entry<String, EventHandler> entry : handlers.entrySet()) {
			// String beanName = entry.getKey();
			EventHandler handler = entry.getValue();
			List<EventType> supportedTypes = handler.getSupportTypes();
			for(EventType type : supportedTypes) {
				List<EventHandler> chain = HANDLER_MAPPING.get(type);
				if(chain == null) {
					chain = new LinkedList<EventHandler>();
					HANDLER_MAPPING.put(type, chain);
				}
				chain.add(handler);
			}
		}
		
		// 2. Start a thread as a daemon.
		new Thread(new EventMonitorThread(), "EventMonitorThread-0").start();
	}
	
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		APPLICATION_CONTEXT = applicationContext;
	}
}
