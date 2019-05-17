package top.ourck.async.handler;

import java.util.List;

import top.ourck.async.EventModel;
import top.ourck.async.EventType;

public interface EventHandler {

	void doHandle(EventModel e);
	List<EventType> getSupportTypes();
	
}
