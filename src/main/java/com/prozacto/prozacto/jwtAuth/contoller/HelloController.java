package com.prozacto.prozacto.jwtAuth.contoller;

import com.prozacto.prozacto.Entity.User.User;
import com.prozacto.prozacto.dao.UserDao;
import com.prozacto.prozacto.jwtAuth.utils.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private UserDao userRepo;

    @GetMapping("/")
    public String greet(){
        return new String("main");
    }

    @GetMapping("/hello")
    public String greetAll(){
        return new String("hello");
    }

    @GetMapping("/admin")
    public String greetAdmin(){
        return new String("admin");
    }

    @PostMapping("/signup")
    public User addUser(@RequestBody User user){
        user.setPassword(Hashing.getEncoder().encode(user.getPassword()));
        userRepo.save(user);
        return user;
    }

}

//    @PreAuthorize("hasAnyRole('ADMIN','USER')")
