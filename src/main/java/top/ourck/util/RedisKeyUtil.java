package top.ourck.util;

public class RedisKeyUtil {

	private static final String SPLIT = ":";
	private static final String LIKE_PFX = "TOP:OURCK:WEBSITEWITHSPRING:LIKE";
	private static final String DISLIKE_PFX = "TOP:OURCK:WEBSITEWITHSPRING:DISLIKE";
	private static final String EVENT_PFX = "TOP:OURCK:WEBSITEWITHSPRING:EVENT";
	
	public static String getLikeKey(int entityType, int entityId) {
		return LIKE_PFX
				+ SPLIT + String.valueOf(entityType)
				+ SPLIT + String.valueOf(entityId);
	}
	
	public static String getDislikeKey(int entityType, int entityId) {
		return DISLIKE_PFX
				+ SPLIT + String.valueOf(entityType)
				+ SPLIT + String.valueOf(entityId);
	}
	
	public static String getEventQueueKey() {
		return EVENT_PFX;
	}
}
