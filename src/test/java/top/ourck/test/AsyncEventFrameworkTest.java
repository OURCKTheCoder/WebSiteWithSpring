package top.ourck.test;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import top.ourck.async.EventConsumer;
import top.ourck.async.EventModel;
import top.ourck.async.EventProducer;
import top.ourck.async.EventType;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AsyncEventFrameworkTest {

	@Autowired
	private EventProducer producer;
	
	@Autowired
	private EventConsumer consumer;
	
	@Test
	public void asyncEventFrameworkTest() {
		EventModel e = new EventModel(EventType.LIKE, 0, 0, 0, 0, null);
		producer.fireEvent(e);
	}
}
