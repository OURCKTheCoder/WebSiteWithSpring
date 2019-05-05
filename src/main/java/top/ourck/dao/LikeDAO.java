package top.ourck.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * TODO Redis操作作为DAO是否正确？
 * @author ourck
 */
@Component
public class LikeDAO {
	private static final Logger logger = LoggerFactory.getLogger("JedisLogger");
	
	@Autowired
	private JedisPool jedisPool;
	
	public long sadd(String key, String val) {
		try(Jedis jedis = jedisPool.getResource()) { // JDK 1.7 AutoCloseable
			return jedis.sadd(key, val);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return -1;
		}
	}
	
	public long srem(String key, String val) {
		try(Jedis jedis = jedisPool.getResource()) {
			return jedis.srem(key, val);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return -1;
		}
	}
	
	public boolean sismember(String key, String val) {
		try(Jedis jedis = jedisPool.getResource()) {
			return jedis.sismember(key, val);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}
	
	public long scard(String key) {
		try(Jedis jedis = jedisPool.getResource()) {
			return jedis.scard(key);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return -1;
		}
	}
}
