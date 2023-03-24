package com.triakobah.TaskParser.core.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@ConfigurationProperties(prefix = "cache")
@EnableCaching
@Data
public class JobCachingConfig {

    public static final String NAME = "jobs";

    public static final long CACHEABLE_TASKS_MIN_SIZE = 1_000;

    public static final String TASKS_MIN_SIZE_SpEL = "#job.tasks.size() > " + CACHEABLE_TASKS_MIN_SIZE;

    private int size; //'cache.size' key in the application.properties file
    private int expirationTime; //'cache.expiration-time' key in the application.properties file

    @Bean
    public Caffeine<Object, Object> caffeineConfig() {
        return Caffeine.newBuilder().expireAfterWrite(expirationTime, TimeUnit.MINUTES).maximumSize(size);
    }

    @Bean
    public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(caffeine);
        return caffeineCacheManager;
    }

}
