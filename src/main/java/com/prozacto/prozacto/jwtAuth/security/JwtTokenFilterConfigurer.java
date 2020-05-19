package com.prozacto.prozacto.jwtAuth.security;


import com.google.common.cache.Cache;
import com.prozacto.prozacto.service.CacheService;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * configures the Jwt token filter
 */
public class JwtTokenFilterConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private JwtTokenProvider jwtTokenProvider;
    private CacheService cacheService;

    public JwtTokenFilterConfigurer(JwtTokenProvider jwtTokenProvider, CacheService cacheService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.cacheService = cacheService;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        JwtTokenFilter customJwtTokenFilter = new JwtTokenFilter(jwtTokenProvider, cacheService);
        http.addFilterBefore(customJwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

}