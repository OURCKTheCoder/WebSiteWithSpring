package top.ourck.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import top.ourck.dao.RedisDAO;
import top.ourck.util.RedisKeyUtil;

@Service
public class EventProducer {

	private static final Logger logger = LoggerFactory.getLogger("logger");
	
	@Autowired
	private RedisDAO redisDAO;
	
	public boolean fireEvent(EventModel e) {
		try {
			String key = RedisKeyUtil.getEventQueueKey();
			redisDAO.lpush(key, JSONObject.toJSONString(e));
			return true;
		} catch (Exception ex) {
			logger.error(ex.getMessage());	
			return false;
		}
	}
}
