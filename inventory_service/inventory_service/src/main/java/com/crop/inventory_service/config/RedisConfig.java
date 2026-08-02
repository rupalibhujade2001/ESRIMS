package com.crop.inventory_service.config;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

@Configuration
public class RedisConfig {

	@Bean("myCacheManager")
	public RedisCacheManager cacheManager(
	        RedisConnectionFactory connectionFactory) {
		
		RedisCacheConfiguration cacheConfiguration =
		        RedisCacheConfiguration.defaultCacheConfig();
		cacheConfiguration = cacheConfiguration.entryTtl(Duration.ofMinutes(10));
		cacheConfiguration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(
                new GenericJackson2JsonRedisSerializer()));
		System.out.println(
			    RedisSerializationContext.SerializationPair
			        .fromSerializer(new GenericJackson2JsonRedisSerializer())
			);
	    System.out.println("******** Custom Redis Cache Manager Loaded ********");
	
		
				return  RedisCacheManager.builder(connectionFactory)
				        .cacheDefaults(cacheConfiguration)
				        .build();

	}
}
 	