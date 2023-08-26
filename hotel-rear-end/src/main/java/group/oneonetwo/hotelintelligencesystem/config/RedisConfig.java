package group.oneonetwo.hotelintelligencesystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.net.UnknownHostException;

/**
 * @author 文
 * @description redis配置类
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<Object, Object> template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);//序列化为Json
        template.setDefaultSerializer(serializer);
        return template;
    }

    @Bean
    RedisCacheManager cacheManager(RedisConnectionFactory factory){
        //创建默认RedisCacheWriter
        RedisCacheWriter cacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(factory);

        //创建默认RedisCacheConfiguration并使用GenericJackson2JsonRedisSerializer构造的		SerializationPair对value进行转换
        //创建GenericJackson2JsonRedisSerializer的json序列化器
        GenericJackson2JsonRedisSerializer jsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        //使用json序列化器构造出对转换Object类型的SerializationPair序列化对
        RedisSerializationContext.SerializationPair<Object> serializationPair = RedisSerializationContext.SerializationPair.fromSerializer(jsonRedisSerializer);
        //将可以把Object转换为json的SerializationPair传入RedisCacheConfiguration
        //使得RedisCacheConfiguration在转换value时使用定制序列化器
        RedisCacheConfiguration cacheConfiguration=RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(serializationPair);

        RedisCacheManager cacheManager = new RedisCacheManager(cacheWriter,cacheConfiguration);
        return cacheManager;
    }
}
