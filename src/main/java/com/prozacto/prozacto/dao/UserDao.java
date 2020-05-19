package com.prozacto.prozacto.dao;

import com.prozacto.prozacto.Entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDao extends JpaRepository<User, Integer> {

    List<User> findAllByNameLikeAndUserType(String name, Integer type);
    User findByUsernameOrContactNumber(String username , String contactNumber);
    List<User> findAllByUserType(Integer userType);
    User findByUsername(String userName);
    boolean existsByName(String userName);
}