package top.ourck.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.ourck.dao.RedisDAO;

@Service
public class EventProducer {

	@Autowired
	private RedisDAO redisDAO;
	
	public void fireEvent(EventModel e) {
		
	}
}
