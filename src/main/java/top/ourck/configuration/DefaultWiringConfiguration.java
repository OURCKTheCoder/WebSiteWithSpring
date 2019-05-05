package top.ourck.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisPool;

@Configuration
public class DefaultWiringConfiguration {

	@Bean
	public JedisPool jedisPool() {
		return new JedisPool();
	}
	
}
