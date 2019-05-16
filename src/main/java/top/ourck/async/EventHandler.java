package top.ourck.async;

import java.util.List;

public interface EventHandler {

	void doHandle(EventModel e);
	List<EventType> getSupportTypes();
	
}
