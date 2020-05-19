package com.prozacto.prozacto.dao;

import com.prozacto.prozacto.Entity.User.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientDao extends JpaRepository<Patient, Integer> {
    Patient findByUserId(Integer userId);
    List<Patient> findAllByUserIdIn(List<Integer> ids);
}
