package top.ourck.async.handler;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import top.ourck.async.EventModel;
import top.ourck.async.EventType;

@Component
public class LikeHandler implements EventHandler {

	private static final List<EventType> SUPPORTED_EVENT_TYPES;
	static {
		// Arrays.asList() returns a FIXED list, which means you CANNOT add() anything into it.
		SUPPORTED_EVENT_TYPES = Arrays.asList(EventType.LIKE);
	}

	
	@Override
	public void doHandle(EventModel e) {
		System.out.println("Get an event! " + e);
	}

	@Override
	public List<EventType> getSupportTypes() {
		return SUPPORTED_EVENT_TYPES;
	}

}
