package com.prozacto.prozacto.jwtAuth;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@Data
public class AuthConfiguration {

    @Value("${rolesFilePath}")
    private String rolesFilePath;

    @Value("${validityInMilliseconds}")
    private Integer validityInMilliseconds;

    @Value("${cacheMaxSize}")
    private Integer cacheMaxSize;
}