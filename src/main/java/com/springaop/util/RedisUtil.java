package com.springaop.util;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * redis缓存操作
 */
@Component
public class RedisUtil {

    private static final Logger log = LoggerFactory.getLogger(RedisUtil.class);

    private static RedisTemplate<String, byte[]> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, byte[]> redisTemplate) {
        RedisUtil.redisTemplate = redisTemplate;
    }

    /**
     * 获取bean类型数据
     *
     * @return
     */
    public static String getString(String key) {

        return redisTemplate.execute((RedisCallback<String>) connection -> {
            try {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] loanListByte = connection.get(serializer.serialize(key));

                if (loanListByte == null) {
                   return null;
                }
                return new String(loanListByte);
            } catch (Exception e) {
                log.error("获取redis数据异常：", e);
                throw new RuntimeException("获取redis数据异常");
            } finally {
                connection.close();
            }
        });
    }

    /**
     * set 缓存
     *
     * @param key
     * @param t
     * @param <T>
     * @return
     */
    public static <T> Boolean set(String key, final T t, long timeOut) {

        return redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            try {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] bKey = serializer.serialize(key);
                String valueStr = JSON.toJSONString(t);
                byte[] bValue = serializer.serialize(valueStr);
                connection.set(bKey, bValue);
                //设置用户信息过期时间
                connection.expire(bKey, timeOut);
            } catch (Exception e) {
                log.error("set redis数据异常：", e);
                throw new RuntimeException("set redis数据异常");
            }
            return true;
        });
    }

    /**
     * 删除redis缓存
     *
     * @param key
     * @return
     */
    public static boolean delete(String key) {
        return redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            try {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] bKey = serializer.serialize(key);

                Long delRes = connection.del(bKey);

            } catch (Exception e) {
                throw new RuntimeException("删除用户借款信息缓存信息异常" + e);
            }
            return true;
        });
    }

}
