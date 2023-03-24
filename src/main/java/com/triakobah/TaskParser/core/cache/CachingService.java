package com.triakobah.TaskParser.core.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class CachingService {
    CacheManager cacheManager;

    @Autowired
    public CachingService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
}
