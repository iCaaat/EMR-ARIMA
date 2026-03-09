package cn.edu.usc.quzhijie.emrservice.common.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

// redis服务类，提供对redis的操作接口
@Service
public class RedisService {
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // 设置键值对
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    // 设置键值对并设置过期时间
    public void set(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    // 获取值
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // 删除键
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    // 检查键是否存在
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    // 设置 Hash 键值对
    public void setHash(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    // 获取 Hash 键值对
    public Object getHash(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    // 设置集合中的值
    public void setSet(String key, Object value) {
        redisTemplate.opsForSet().add(key, value);
    }

    // 获取集合中的所有值
    public Object getSet(String key) {
        return redisTemplate.opsForSet().members(key);
    }
}
