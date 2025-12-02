package com.example.EventBookingApi.Service;

import io.github.bucket4j.Bucket;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimiterService {
    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    public Bucket resolveBucket(String key){
        if(key == null || key.isEmpty()) key = "UNKNOWN";
        return cache.computeIfAbsent(key, this::newBucket);
    }

    private Bucket newBucket(String key){
        return Bucket.builder()
                .addLimit(limit -> limit.capacity(30)
                        .refillGreedy(30, Duration.ofMinutes(1)))
                        .build();
    }
}
