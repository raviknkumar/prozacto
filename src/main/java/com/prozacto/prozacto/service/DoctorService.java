package com.prozacto.prozacto.service;

import com.prozacto.prozacto.Entity.Enrollment;
import com.prozacto.prozacto.Entity.User.Doctor;
import com.prozacto.prozacto.Entity.User.Patient;
import com.prozacto.prozacto.Entity.User.User;
import com.prozacto.prozacto.dao.DoctorDao;
import com.prozacto.prozacto.dao.PatientDao;
import com.prozacto.prozacto.model.EnrollmentDto;
import com.prozacto.prozacto.model.enums.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    @Autowired
    DoctorDao doctorDao;

    @Autowired
    PatientDao patientDao;

    @Autowired
    EnrollmentService enrollmentService;

    @Autowired
    DocumentPermissionService documentPermissionService;

    @Autowired
    UserService userService;

    public Doctor addDoctor(Doctor doctor) {
        return doctorDao.save(doctor);
    }

    public List<Doctor> findDoctorsByClinicId(Integer clinicId) throws Exception {
        List<Enrollment> enrollments = enrollmentService.findAllByClinicId(clinicId);
        List<Integer> doctorIds = enrollments.stream().map(Enrollment::getDoctorId).collect(Collectors.toList());
        return doctorDao.findAllByIdIn(doctorIds);
    }


    public List<Doctor> findByName(String name) {

        List<User> users = userService.findAllByUserNameAndType("%" + name + "%", UserType.DOCTOR.getId());
        List<Integer> userIds = users.stream().map(User::getId).collect(Collectors.toList());
        return doctorDao.findAllByIdIn(userIds);
    }

    public Doctor findIfExists(Integer doctorId) throws Exception {
        Doctor doctor = doctorDao.findById(doctorId).orElse(null);
        if (doctor == null)
            throw new Exception("No Doctor Found with Id: " + doctorId);
        return doctor;
    }

    public boolean checkAccess(Integer doctorUserId, Integer patientUserId) throws Exception {
        Doctor doctor = doctorDao.findByUserId(doctorUserId);
        Patient patient = patientDao.findByUserId(patientUserId);
        List<EnrollmentDto> enrollmentDtos = enrollmentService.findAllByDoctorId(doctor.getId());
        List<Integer> clinicIds = enrollmentDtos.stream().map(EnrollmentDto::getClinicId).collect(Collectors.toList());
        return documentPermissionService.checkAccess(clinicIds, patient.getId(), doctor.getId());
    }
}
