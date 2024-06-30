package com.f1v3.redis.dto;

import lombok.Data;

@Data
public class RedisCreateRequest {

    private String key;

    private String value;
}
