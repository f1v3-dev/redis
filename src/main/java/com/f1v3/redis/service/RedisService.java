package com.f1v3.redis.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisService {

    private final StringRedisTemplate stringRedisTemplate;

    public void getRedisStringValue(String key) {

        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();

        log.info("Redis kye = {}", key);
        log.info("Redis value = {}", operations.get(key));
    }

    public void setRedisStringValue(String key, String value) {

        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.set(key, value);

        log.info("Redis key = {}", key);
        log.info("Redis value = {}", operations.get(key));
    }
}
