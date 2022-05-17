package com.xc.joy.service;

import com.xc.joy.lock.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author lxcecho 909231497@qq.com
 * @since 15.05.2022
 */
@Service
@Slf4j
public class SchedulerService {

    @Autowired
    private RedisTemplate redisTemplate;

    public void sendSms() {
        try (RedisLock redisLock = new RedisLock(redisTemplate, "autoSms", 30)) {
            if (redisLock.getLock()) {
                log.info("send msg to 183xxxxx2726");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
