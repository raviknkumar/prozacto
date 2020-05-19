package com.prozacto.prozacto.dao;

import com.prozacto.prozacto.Entity.DocumentPermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface DocumentPermissionDao extends JpaRepository<DocumentPermission, Integer> {

    DocumentPermission findDistinctByDoctorIdAndClinicId(Integer doctorId, Integer clinicId);
    DocumentPermission findDistinctByClinicId(Integer clinicId);
    List<DocumentPermission> findAllByPatientIdAndClinicIdInAndAccessDateGreaterThanEqual(Integer patientId, List<Integer> clinicIds,
                                                                                           String currentDate);
    DocumentPermission findDistinctByPatientIdAndDoctorIdAndAccessDateGreaterThanEqual(Integer patientId, Integer doctorId, String date);
}
