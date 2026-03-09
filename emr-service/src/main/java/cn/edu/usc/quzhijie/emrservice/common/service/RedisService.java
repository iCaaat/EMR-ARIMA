package cn.edu.usc.quzhijie.emrservice.common.service;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

// redis服务类，提供对redis的操作接口
@Service
public class RedisService {
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 设置缓存
     *
     * @param key   Redis 中的键
     * @param value Redis 中的值
     */
    public void set(String key, Object value) {
        try {
            ValueOperations<String, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
        } catch (Exception e) {
            // 记录日志
            throw new RuntimeException("设置缓存失败，key：" + key, e);
        }
    }

    /**
     * 设置缓存，并指定过期时间
     *
     * @param key     Redis 中的键
     * @param value   Redis 中的值
     * @param timeout 过期时间
     * @param unit    时间单位
     */
    public void set(String key, Object value, long timeout, TimeUnit unit) {
        try {
            ValueOperations<String, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value, timeout, unit);
        } catch (Exception e) {
            // 记录日志
            throw new RuntimeException("设置缓存失败，key：" + key + ", timeout：" + timeout, e);
        }
    }

    /**
     * 获取缓存
     *
     * @param key Redis 中的键
     * @return Redis 中的值
     */
    public Object get(String key) {
        try {
            ValueOperations<String, Object> operations = redisTemplate.opsForValue();
            return operations.get(key);
        } catch (Exception e) {
            // 记录日志
            throw new RuntimeException("获取缓存失败，key：" + key, e);
        }
    }

    /**
     * 删除缓存
     *
     * @param key Redis 中的键
     */
    public void delete(String key) {
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            // 记录日志
            throw new RuntimeException("删除缓存失败，key：" + key, e);
        }
    }

    /**
     * 判断缓存是否存在
     *
     * @param key Redis 中的键
     * @return true 如果存在，false 否则
     */
    public boolean exists(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            // 记录日志
            throw new RuntimeException("检查缓存是否存在失败，key：" + key, e);
        }
    }

    /**
     * 获取所有匹配的键
     *
     * @param pattern 匹配的模式
     * @return 匹配的键集合
     */
    public Set<String> keys(String pattern) {
        try {
            return redisTemplate.keys(pattern);
        } catch (Exception e) {
            // 记录日志
            throw new RuntimeException("获取匹配的键失败，pattern：" + pattern, e);
        }
    }

    /**
     * 设置哈希值
     *
     * @param key     Redis 中的哈希键
     * @param hashKey 哈希中的字段
     * @param value   哈希中的值
     */
    public void hset(String key, String hashKey, Object value) {
        try {
            HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
            hashOperations.put(key, hashKey, value);
        } catch (Exception e) {
            // 记录日志
            throw new RuntimeException("设置哈希值失败，key：" + key + ", hashKey：" + hashKey, e);
        }
    }

    /**
     * 获取哈希值
     *
     * @param key     Redis 中的哈希键
     * @param hashKey 哈希中的字段
     * @return 哈希中的值
     */
    public Object hget(String key, String hashKey) {
        try {
            HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
            return hashOperations.get(key, hashKey);
        } catch (Exception e) {
            // 记录日志
            throw new RuntimeException("获取哈希值失败，key：" + key + ", hashKey：" + hashKey, e);
        }
    }

    /**
     * 删除哈希字段
     *
     * @param key     Redis 中的哈希键
     * @param hashKey 哈希中的字段
     */
    public void hdel(String key, String hashKey) {
        try {
            HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
            hashOperations.delete(key, hashKey);
        } catch (Exception e) {
            // 记录日志
            throw new RuntimeException("删除哈希字段失败，key：" + key + ", hashKey：" + hashKey, e);
        }
    }
}
