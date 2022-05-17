package com.xc.joy.lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.core.types.Expiration;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author lxcecho 909231497@qq.com
 * @since 15.05.2022
 */
@Slf4j
public class RedisLock implements AutoCloseable {

    private RedisTemplate redisTemplate;

    private String key;

    private String value;

    /**
     * 单位：秒
     */
    private int expireTime;

    public RedisLock(RedisTemplate redisTemplate, String key, int expireTime) {
        this.redisTemplate = redisTemplate;
        this.key = key;
        this.expireTime = expireTime;
        this.value = UUID.randomUUID().toString();
    }

    /**
     * 获取分布式锁
     *
     * @return
     */
    public boolean getLock() {
        RedisCallback<Boolean> redisCallback = connection -> {
            // 设置 NX
            RedisStringCommands.SetOption setOption = RedisStringCommands.SetOption.ifAbsent();
            // 设置过期时间
            Expiration expiration = Expiration.seconds(expireTime);
            // 序列化 key
            byte[] redisKey = redisTemplate.getKeySerializer().serialize(key);
            // 序列化 value
            byte[] redisValue = redisTemplate.getValueSerializer().serialize(value);
            // 执行 setnx 操作
            Boolean result = connection.set(redisKey, redisValue, expiration, setOption);
            return result;
        };

        // 获取分布式锁
        Boolean lock = (Boolean) redisTemplate.execute(redisCallback);
        return lock;
    }

    /**
     * 释放分布式锁
     *
     * @return
     */
    public boolean unlock() {
        String script = "if redis.call(\"get\",KEYS[1]) == ARGV[1] then\n" +
                "    return redis.call(\"del\",KEYS[1])\n" +
                "else\n" +
                "    return 0\n" +
                "end";
        RedisScript<Boolean> redisScript = RedisScript.of(script, Boolean.class);
        List<String> keys = Arrays.asList(key);

        Boolean result = (Boolean) redisTemplate.execute(redisScript, keys, value);
        log.info("释放锁的结果：" + result);
        return result;
    }

    @Override
    public void close() throws Exception {
        unlock();
    }
}
