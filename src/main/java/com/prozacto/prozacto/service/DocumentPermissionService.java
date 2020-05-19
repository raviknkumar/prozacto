package com.prozacto.prozacto.service;

import com.prozacto.prozacto.Entity.DocumentPermission;
import com.prozacto.prozacto.Entity.User.Doctor;
import com.prozacto.prozacto.converter.DocumentPermissionConverter;
import com.prozacto.prozacto.dao.DocumentPermissionDao;
import com.prozacto.prozacto.model.DocumentPermissionDto;
import com.prozacto.prozacto.model.EnrollmentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentPermissionService {

    @Autowired
    DocumentPermissionConverter documentPermissionConverter;

    @Autowired
    DocumentPermissionDao documentPermissionDao;

    public DocumentPermissionDto grantAccess(DocumentPermissionDto documentPermissionDto) {

        Integer clinicId = documentPermissionDto.getClinicId();
        DocumentPermission documentPermission = documentPermissionDao.findDistinctByClinicId(clinicId); // one Permission for Entire Clinic

        if(documentPermission == null)
            documentPermission = documentPermissionConverter.convertModelToEntity(documentPermissionDto);
        else {
            // Extend Access till Appointment Date in case of ReSchedule
            documentPermission.setAccessDate(documentPermissionDto.getAccessDate());
        }

        documentPermission = documentPermissionDao.save(documentPermission);
        return documentPermissionConverter.convertEntityToModel(documentPermission);
    }

    public void deleteAccess(DocumentPermissionDto documentPermissionDto) {

        Integer clinicId = documentPermissionDto.getClinicId();
        Integer doctorId = documentPermissionDto.getDoctorId();

        DocumentPermission documentPermission = documentPermissionDao.findDistinctByDoctorIdAndClinicId(doctorId, clinicId);
        documentPermission.setDeleted(true);
        documentPermissionDao.save(documentPermission);
    }

    public boolean checkAccess(List<Integer> clinicIds, Integer patientId, Integer doctorId) throws Exception {
        String currentDate = LocalDate.now().toString();
        List<DocumentPermission> documentPermissions = documentPermissionDao.findAllByPatientIdAndClinicIdInAndAccessDateGreaterThanEqual(patientId, clinicIds, currentDate);
        if(!CollectionUtils.isEmpty(documentPermissions)){
            return true;
        } else {
            DocumentPermission documentPermission = documentPermissionDao.findDistinctByPatientIdAndDoctorIdAndAccessDateGreaterThanEqual(patientId, doctorId, currentDate);
            return documentPermission != null;
        }
    }

}
