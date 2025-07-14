package com.example.plusproject.infra.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
public class RedisLockRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisLockRepository(RedisTemplate<String, String> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    public Boolean lock(String key){
        return redisTemplate
                .opsForValue()                                                  // String 을 serialise/deserialise 하기 위한 메서드
                .setIfAbsent(key,"lock", Duration.ofMillis(5000));        // key,"lock" 으로 key-value 값 저장을 시도, 5초 동안만 락을 유지
                                                                                // 이 키는 5초 후에 삭제됨. 왜냐면 락을 걸었는데 처리도중 어플리케이션이 죽으면 락 못풀고 키가 영원히 남음 -> deadlock 발생
    }

    public Boolean unlock(String key){
        return redisTemplate.delete(key);
    }

}
