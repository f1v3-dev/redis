# Spring Boot + Redis

- NoSQL인 Redis 사용해보기

## Dependency

- Spring Web
- Spring Data Redis (Access + Driver)

> redis-server를 띄운 상태

## Properties 설정

> jedis와 lettuce 중 lettuce를 사용하였습니다.   
> [향로님 - Jedis 보다 Lettuce 를 쓰자](https://jojoldu.tistory.com/418)

```properties
spring.data.redis.host=localhost
spring.data.redis.port=6379
spring.data.redis.password=
spring.data.redis.lettuce.pool.max-idle=8
spring.data.redis.lettuce.pool.min-idle=0
spring.data.redis.lettuce.pool.max-active=8
spring.data.redis.lettuce.pool.max-wait=-1
```

## RedisTemplate

- RedisTemplate을 사용하여 Redis 서버에 저장되어있는 데이터를 조작할 수 있다.
- RestTemplate과 비슷한 역할을 하는듯?

_RedisService.java_

```java
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

        ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
        log.info("Redis kye = {}", key);
        log.info("Redis value = {}", stringStringValueOperations.get(key));
    }
}

```

- `StringRedisTemplate`을 사용하여 다양한 타입의 자료구조를 다룰 수 있다.

![stringRedisTemplate.png](images/stringRedisTemplate.png)

- 위와 같은 메서드를 통해 직렬화 및 역직렬화를 할 수 있어 유용

## Spring Session을 Redis로 관리해보자

- Spring Session Data Redis를 사용하여 세션을 Redis로 관리해보자

_pom.xml_

```xml

<dependency>
    <groupId>org.springframework.session</groupId>
    <artifactId>spring-session-data-redis</artifactId>
</dependency>
```

_RedisApplication.java_

```java
package com.f1v3.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession
@SpringBootApplication
public class RedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class, args);
    }

}

```

- `@EnableRedisHttpSession`을 사용하면 세션을 Redis로 관리할 수 있게 된다.
- 자신의 Session 값을 얻어오는 Controller를 만들어보자

_RedisController.java_

```java

@GetMapping("/getSessionId")
public String getSessionId(HttpSession session) {
    return session.getId();
}
```

위의 API를 호출한다면 Redis에 저장된 세션 ID를 얻을 수 있다.  
또한, Redis CLI를 통해 저장된 세션을 조회해보자.

_Redis CLI_

```shell
keys *
```

![img.png](images/spring_session.png)

- 해당 데이터는 `hashMap` 형태로 저장되어 있음!

> Redis - hashmap  
> ![img.png](images/redis_hashes.png)

- 해시 맵(hashMap)은 `hget` 명령어를 통해 조회할 수 있다.
- `hgetall` 명령어로 모든 데이터를 조회해보자

![img.png](images/hgetall.png)

---

### 참고

> [Springboot + Redis 연동하는 예제](https://oingdaddy.tistory.com/310)
