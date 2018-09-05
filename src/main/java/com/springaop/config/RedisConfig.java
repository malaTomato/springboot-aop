package com.springaop.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by guofeng.qin on 2017/7/9 0009.
 */
@Configuration
@EnableAutoConfiguration
public class RedisConfig {

    @Bean(name = "spring.jedis.pool.config")
    public JedisPoolConfig getJedisPoolConfig(
            @Value("${spring.jedis.maxTotal}") int maxTotal,
            @Value("${spring.jedis.maxIdle}") int maxIdle,
            @Value("${spring.jedis.minIdle}") int minIdle,
            @Value("${spring.jedis.maxWait}") long maxWait,
            @Value("${spring.jedis.minEvictableIdle}") long minEvictableIdle,
            @Value("${spring.jedis.timeBetweenEviction}") long timeBetweenEviction) {
        JedisPoolConfig config = new JedisPoolConfig();

        if (maxTotal > 0) {
            config.setMaxTotal(maxTotal);
        }
        if (maxIdle > 0) {
            config.setMaxIdle(maxIdle);
        }
        if (minIdle > 0) {
            config.setMinIdle(minIdle);
        }
        if (maxWait > 0) {
            config.setMaxWaitMillis(maxWait);
        }
        if (minEvictableIdle > 0) {
            config.setMinEvictableIdleTimeMillis(minEvictableIdle);
        }
        if (timeBetweenEviction > 0) {
            config.setTimeBetweenEvictionRunsMillis(timeBetweenEviction);
        }
        return config;
    }

    @Bean(name = "spring.jedis.pool.factory")
    public JedisConnectionFactory getJedisConnectionFactory(
            @Qualifier("spring.jedis.pool.config") JedisPoolConfig jedisPoolConfig,
            @Value("${spring.jedis.clientName}") String clientName,
            @Value("${spring.jedis.host}") String host,
            @Value("${spring.jedis.port}") int port) {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(jedisPoolConfig);
        jedisConnectionFactory.setHostName(host);
        jedisConnectionFactory.setPort(port);
        if (!StringUtils.isEmpty(clientName)) {
            jedisConnectionFactory.setClientName(clientName);
        }

        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate<?, ?> getRedisTemplate(@Qualifier("spring.jedis.pool.factory") JedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate<?, ?> template = new StringRedisTemplate(jedisConnectionFactory);
        return template;
    }
}
