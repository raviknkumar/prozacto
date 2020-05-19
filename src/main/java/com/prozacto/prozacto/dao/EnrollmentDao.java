package com.prozacto.prozacto.dao;

import com.prozacto.prozacto.Entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentDao extends JpaRepository<Enrollment, Integer> {
    Optional<Enrollment> findById(Integer id);
    List<Enrollment> findAllByClinicId(Integer clinicId);
    List<Enrollment> findAllByDoctorId(Integer doctorId);
}
