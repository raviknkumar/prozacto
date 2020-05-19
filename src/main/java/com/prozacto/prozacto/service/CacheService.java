package com.prozacto.prozacto.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.prozacto.prozacto.Entity.User.User;
import com.prozacto.prozacto.jwtAuth.AuthConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Service
public class CacheService {

    private Cache<String, User> jwtTokenToUserCache;

    @Autowired
    private AuthConfiguration authConfiguration;

    public User getUser(String jwtToken) {
        return jwtTokenToUserCache.getIfPresent(jwtToken);
    }

    public void setUser(String jwtToken, User user) {
        jwtTokenToUserCache.put(jwtToken, user);
    }

    @PostConstruct
    public void init() {
        long duration = authConfiguration.getValidityInMilliseconds() / 10;
        Integer cacheMaxSize = authConfiguration.getCacheMaxSize();
        jwtTokenToUserCache = CacheBuilder.newBuilder()
                .maximumSize(cacheMaxSize)
                .expireAfterWrite(duration, TimeUnit.HOURS)
                .recordStats().build();
    }
}