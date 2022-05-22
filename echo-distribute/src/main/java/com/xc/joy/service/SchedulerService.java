package com.xc.joy.service;

import com.xc.joy.lock.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author lxcecho 909231497@qq.com
 * @since 15.05.2022
 */
@Slf4j
@Service
public class SchedulerService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Scheduled(cron = "0/5 * * * * ?")
    public void sendSms() {
        // 获取分布式锁
        try (RedisLock redisLock = new RedisLock(redisTemplate, "autoSms", 30)) {
            if (redisLock.getLock()) {
                log.info("向 183777XXXXX 发送短信！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
