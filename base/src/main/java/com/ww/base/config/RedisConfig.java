package com.ww.base.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;


//@Cacheable 用来标明该方法返回的结果是否被缓存，或者通过缓存返回结果

@Configuration
@Slf4j
public class RedisConfig  extends CachingConfigurerSupport {

    private final RedisConnectionFactory redisConnectionFactory;

    RedisConfig(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }


    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // set key serializer
        StringRedisSerializer strSerializer = TedisCacheManager.STRING_SERIALIZER;
        // 设置key序列化类，否则key前面会多了一些乱码
        template.setKeySerializer(strSerializer);
        template.setHashKeySerializer(strSerializer);

        // fastjson serializer
        GenericFastJsonRedisSerializer fastSerializer = TedisCacheManager.FASTJSON_SERIALIZER;
        template.setValueSerializer(fastSerializer);
        template.setHashValueSerializer(fastSerializer);
        // 如果 KeySerializer 或者 ValueSerializer 没有配置，则对应的 KeySerializer、ValueSerializer 才使用这个 Serializer
        template.setDefaultSerializer(fastSerializer);

        log.info("redis: {}", redisConnectionFactory);
        LettuceConnectionFactory factory = (LettuceConnectionFactory) redisConnectionFactory;
        log.info("spring.redis.database: {}", factory.getDatabase());
        log.info("spring.redis.host: {}", factory.getHostName());
        log.info("spring.redis.port: {}", factory.getPort());
        log.info("spring.redis.timeout: {}", factory.getTimeout());
        log.info("spring.redis.password: {}", factory.getPassword());

        // factory
        template.setConnectionFactory(redisConnectionFactory);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return (o, method, objects) -> {
            StringBuilder sb = new StringBuilder(32);
            sb.append(o.getClass().getSimpleName());
            sb.append(".");
            sb.append(method.getName());
            if (objects.length > 0) {
                sb.append("#");
            }
            String sp = "";
            for (Object object : objects) {
                sb.append(sp);
                if (object == null) {
                    sb.append("NULL");
                } else {
                    sb.append(object.toString());
                }
                sp = ".";
            }
            return sb.toString();
        };
    }

    /**
     * 配置 RedisCacheManager，使用 cache 注解管理 redis 缓存
     */
    @Bean
    @Override
    public CacheManager cacheManager() {
        // 初始化一个RedisCacheWriter
        RedisCacheWriter cacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);

        // 设置默认过期时间：30 分钟
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(30))
                // .disableCachingNullValues()
                // 使用注解时的序列化、反序列化
                .serializeKeysWith(TedisCacheManager.STRING_PAIR)
                .serializeValuesWith(TedisCacheManager.FASTJSON_PAIR);

        // Map<String, RedisCacheConfiguration> caches = new HashMap<>();
        // // 缓存配置
        // RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
        //         .entryTtl(Duration.ofSeconds(60))
        //         .disableCachingNullValues()
        //         // .prefixKeysWith("redis.service")
        //         .serializeKeysWith(stringPair)
        //         .serializeValuesWith(fastJsonPair);
        // caches.put("redis.service", config);
        // return new TedisCacheManager(cacheWriter, defaultCacheConfig, caches);

        return new TedisCacheManager(cacheWriter, defaultCacheConfig);

    }
}
