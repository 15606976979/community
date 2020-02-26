package com.example.redisConfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 完成对redis的整合的配置信息
 *
 */
@Configuration
public class RedisConfig {
    @Bean
    @ConfigurationProperties(prefix="spring.redis")
    public RedisTemplate<String,Object> redisTemplate(){
        //1.创建JedisPoolConfig对象，在该对象中完成一些连接池配置
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 配置最大空闲数
        jedisPoolConfig.setMaxIdle(10);
        // 配置最小空闲树
        jedisPoolConfig.setMinIdle(5);
        // 配置最大连接数
        jedisPoolConfig.setMaxTotal(20);

        //2.创建JedisConnectionFactory:配置redis连接信息
        //单机版jedis
        RedisStandaloneConfiguration redisStandaloneConfiguration =
                new RedisStandaloneConfiguration();
        //设置redis服务器的host或者ip地址
        redisStandaloneConfiguration.setHostName("192.168.234.128");
        //设置redis的服务的端口号
        redisStandaloneConfiguration.setPort(6379);
        //设置默认使用的数据库
        redisStandaloneConfiguration.setDatabase(0);
        //设置密码
        redisStandaloneConfiguration.setPassword("123456");

        //获得默认的连接池构造器(怎么设计的，为什么不抽象出单独类，供用户使用呢)
        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpcb =
                (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder)JedisClientConfiguration.builder();
        //指定jedisPoolConifig来修改默认的连接池构造器（真麻烦，滥用设计模式！）
        jpcb.poolConfig(jedisPoolConfig);
        //通过构造器来构造jedis客户端配置
        JedisClientConfiguration jedisClientConfiguration = jpcb.build();
        //单机配置 + 客户端配置 = jedis连接工厂
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);

        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        // 关联
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        // 为key设置序列化去
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        return redisTemplate;
    }
}
