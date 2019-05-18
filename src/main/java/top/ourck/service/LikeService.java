package top.ourck.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.ourck.dao.RedisDAO;
import top.ourck.util.RedisKeyUtil;

@Service
public class LikeService {
	/**
	 * 键命名策略：(K, V) -> (LIKE:entityType:entityId, userId)
	 * 即：实体为键，用户为值。
	 */
	
	@Autowired
	private RedisDAO redisDAO;

	public int getLikeStatus(int userId, int entityType, int entityId) {
		String entityLikeKey = RedisKeyUtil.getLikeKey(entityId, entityType);
		if(redisDAO.sismember(entityLikeKey, String.valueOf(userId)))
			return 1;
		else {
			String entityDislikeKey = RedisKeyUtil.getDislikeKey(entityId, entityType);
			return redisDAO.sismember(entityDislikeKey, String.valueOf(userId)) ? -1 : 0;
		}
	}
	
	/**
	 * 业务上，当一个用户点击“喜欢”按钮时：
	 * <ol>
	 * <li>如果该用户之前没喜欢过，允许本次喜欢；（这点由Redis集合类型的元素唯一性确保）</li>
	 * <li>在1的基础上，若该用户之前不喜欢过，则需要去除之前的不喜欢记录；</li>
	 * <li>如果该用户之前喜欢过，不允许本次喜欢。</li>
	 * </ol>
	 * @param userId 用户
	 * @param entityType 实体类型（如评论，新闻）
	 * @param entityId 实体id
	 * @return 更新后的喜欢数
	 */
	public long incLikeOn(int userId, int entityType, int entityId) {
		String entityLikeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
		redisDAO.sadd(entityLikeKey, String.valueOf(userId));
		
		String entityDislikeKey = RedisKeyUtil.getDislikeKey(entityType, entityId);
		redisDAO.srem(entityDislikeKey, String.valueOf(userId));
		
		return redisDAO.scard(entityLikeKey);
	}
	
	/**
	 * 业务上，当一个用户点击“不喜欢”按钮时：
	 * <ol>
	 * <li>如果该用户之前没不喜欢过，允许本次不喜欢；（这点由Redis集合类型的元素唯一性确保）</li>
	 * <li>在1的基础上，若该用户之前喜欢过，则需要去除之前的喜欢记录；</li>
	 * <li>如果该用户之前不喜欢过，不允许本次不喜欢。</li>
	 * </ol>
	 * @param userId 用户
	 * @param entityType 实体类型（如评论，新闻）
	 * @param entityId 实体id
	 * @return 更新后的 #喜欢# 数而非 #不喜欢# 数
	 */
	public long incDislikeOn(int userId, int entityType, int entityId) {
		String entityDislikeKey = RedisKeyUtil.getDislikeKey(entityType, entityId);
		redisDAO.sadd(entityDislikeKey, String.valueOf(userId));

		String entityLikeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
		redisDAO.srem(entityLikeKey, String.valueOf(userId));
		
		return redisDAO.scard(entityLikeKey);
	}
	
}
