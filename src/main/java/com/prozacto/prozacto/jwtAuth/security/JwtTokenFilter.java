package com.prozacto.prozacto.jwtAuth.security;


import com.prozacto.prozacto.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// We should use OncePerRequestFilter since we are doing a database call, there is no point in doing this more than once
public class JwtTokenFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;
    private CacheService cacheService;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider, CacheService cacheService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.cacheService = cacheService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken(httpServletRequest);

        try {
            if (token != null && jwtTokenProvider.validateToken(token)) {
                httpServletRequest.setAttribute(Constants.AUTH_USER, cacheService.getUser(token));
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (Exception ex) {
            //this is very important, since it guarantees the user is not authenticated at all
            SecurityContextHolder.clearContext();
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
            return;
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}