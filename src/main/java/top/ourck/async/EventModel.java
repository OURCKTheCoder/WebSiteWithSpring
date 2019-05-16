package top.ourck.async;

import java.util.Map;

public class EventModel {

	private EventType type;
	private int callerId;
	private int entityId;
	private int entityType;
	private int entityOwnerId;
	private Map<String, String> params;
	
	public EventModel() {
		// TODO Auto-generated constructor stub
	}

	public EventModel(EventType type, int callerId, int entityId, int entityType, int entityOwnerId,
			Map<String, String> params) {
		super();
		this.type = type;
		this.callerId = callerId;
		this.entityId = entityId;
		this.entityType = entityType;
		this.entityOwnerId = entityOwnerId;
		this.params = params;
	}

	public EventType getType() {
		return type;
	}

	public int getCallerId() {
		return callerId;
	}

	public int getEntityId() {
		return entityId;
	}

	public int getEntityType() {
		return entityType;
	}

	public int getEntityOwnerId() {
		return entityOwnerId;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public void setCallerId(int callerId) {
		this.callerId = callerId;
	}

	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}

	public void setEntityType(int entityType) {
		this.entityType = entityType;
	}

	public void setEntityOwnerId(int entityOwnerId) {
		this.entityOwnerId = entityOwnerId;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	
}
