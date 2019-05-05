package top.ourck.util;

public class RedisKeyUtil {

	private static final String SPLIT = ":";
	private static final String LIKE_PFX = "LIKE";
	private static final String DISLIKE_PFX = "DISLIKE";
	
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
}
