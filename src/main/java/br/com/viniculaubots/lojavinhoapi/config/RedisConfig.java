package br.com.viniculaubots.lojavinhoapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

@Configuration
public class RedisConfig {

	@Value(value = "${spring.redis.host}")
	private String redisHostName;
	
	@Value(value = "${spring.redis.port}")
	private int redisPort;
	
	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisHostName, redisPort);
		return new JedisConnectionFactory(redisStandaloneConfiguration);
	}
}