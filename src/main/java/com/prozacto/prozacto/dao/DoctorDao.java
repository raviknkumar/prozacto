package com.prozacto.prozacto.dao;

import com.prozacto.prozacto.Entity.User.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorDao extends JpaRepository<Doctor, Integer> {

    List<Doctor> findAllByIdIn(List<Integer> ids);

    Doctor findByUserId(Integer id);

    List<Doctor> findAllByUserIdIn(List<Integer> userIds);
}
