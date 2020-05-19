package com.prozacto.prozacto.jwtAuth.contoller;


import com.prozacto.prozacto.jwtAuth.service.AuthUserService;
import com.prozacto.prozacto.model.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/users")
@Slf4j
public class LoginController {

    @Autowired
    private AuthUserService authUserService;
    @Autowired
    private HttpServletRequest request;

    @PostMapping("/login")
    public void handleLogin(@RequestBody UserDto user, HttpServletRequest httpServletRequest, HttpServletResponse response) {
        String jwtToken = authUserService.signin(user.getUsername(), user.getPassword());
        response.setHeader("Authorization", jwtToken);
        log.info("Entry to: {}", this.getClass().getName());
        log.info("Successfully authenticated. Security context contains: " +
                SecurityContextHolder.getContext().getAuthentication());
    }

    @PostMapping("/sign-up")
    public String handleSignUp(@RequestBody UserDto userDto) throws Exception{
        authUserService.signup(userDto);
        return "Sign up is successfull";
    }
}
