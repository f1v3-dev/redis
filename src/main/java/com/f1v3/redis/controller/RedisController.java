package com.f1v3.redis.controller;

import com.f1v3.redis.dto.RedisCreateRequest;
import com.f1v3.redis.dto.RedisRequest;
import com.f1v3.redis.service.RedisService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RedisController {

    private final RedisService redisService;

    @PostMapping("/getRedisStringValue")
    public void getRedisStringValue(@RequestBody RedisRequest request) {
        redisService.getRedisStringValue(request.getKey());
    }

    @PostMapping("/setRedisStringValue")
    public void setRedisStringValue(@RequestBody RedisCreateRequest request) {
        redisService.setRedisStringValue(request.getKey(), request.getValue());
    }

    @GetMapping("/getSessionId")
    public String getSessionId(HttpSession session) {
        return session.getId();
    }
}
