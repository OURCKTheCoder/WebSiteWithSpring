package top.ourck.dao;

import java.util.List;

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
public class RedisDAO {
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
	
	public long lpush(String key, String val) {
		try(Jedis jedis = jedisPool.getResource()) {
			return jedis.lpush(key, val);
		} catch(Exception e) {
			logger.error(e.getMessage());
			return -1;
		}
	}
	
	public List<String> brpop(int timeout, String key) {
		try(Jedis jedis = jedisPool.getResource()) {
			return jedis.brpop(timeout, key);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
}
