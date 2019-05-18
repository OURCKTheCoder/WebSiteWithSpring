package top.ourck.async;

public enum EventType {
    LIKE(0),
    COMMENT(1),
    LOGIN_EXCEPTION(2),
    MAIL(3);
	
	private int value;
	
	EventType(int val) {
		this.value = val;
	}

	public int getValue() {
		return value;
	}

}
