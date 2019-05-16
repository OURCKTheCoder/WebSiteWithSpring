package top.ourck.async.handler;

import java.util.Arrays;
import java.util.List;

import top.ourck.async.EventHandler;
import top.ourck.async.EventModel;
import top.ourck.async.EventType;

public class LikeHandler implements EventHandler {

	private static final List<EventType> SUPPORTED_EVENT_TYPES;
	static {
		// Arrays.asList() returns a FIXED list, which means you CANNOT add() anything into it.
		SUPPORTED_EVENT_TYPES = Arrays.asList(EventType.LIKE);
	}

	
	@Override
	public void doHandle(EventModel e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<EventType> getSupportTypes() {
		return SUPPORTED_EVENT_TYPES;
	}

}
