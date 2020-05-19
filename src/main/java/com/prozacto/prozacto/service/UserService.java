package com.prozacto.prozacto.service;

import com.prozacto.prozacto.Entity.User.Doctor;
import com.prozacto.prozacto.Entity.User.Patient;
import com.prozacto.prozacto.Entity.User.User;
import com.prozacto.prozacto.converter.DoctorConverter;
import com.prozacto.prozacto.converter.PatientConverter;
import com.prozacto.prozacto.converter.UserConverter;
import com.prozacto.prozacto.dao.DoctorDao;
import com.prozacto.prozacto.dao.PatientDao;
import com.prozacto.prozacto.dao.UserDao;
import com.prozacto.prozacto.model.DoctorDto;
import com.prozacto.prozacto.model.PatientDto;
import com.prozacto.prozacto.model.UserDto;
import com.prozacto.prozacto.model.UserRequestDto;
import com.prozacto.prozacto.model.enums.UserType;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    DoctorDao doctorDao;

    @Autowired
    UserConverter userConverter;

    @Autowired
    DoctorConverter doctorConverter;

    @Autowired
    PatientConverter patientConverter;

    @Autowired
    PatientDao patientDao;

    @Autowired DoctorService doctorService;

    @PersistenceContext(name = "coreTransactionManager")
    private EntityManager entityManager;

    public List<User> findAllByUserNameAndType(String userName, Integer type){
        return userDao.findAllByNameLikeAndUserType(userName, type);
    }

    public UserDto create(UserDto userDto) throws Exception{

        validate(userDto);
        User user = userDao.save(userConverter.convert(userDto));

        if(userDto.getUserType() == UserType.DOCTOR.getId())
        {
            Doctor doctor = new Doctor();
            doctor.setUserId(user.getId());
            doctor.setSpecialization(userDto.getDoctorDetails().getSpecialization());
            doctor.setSubSpecialization(userDto.getDoctorDetails().getSubSpecialization());
            doctor = doctorDao.save(doctor);

            return userConverter.convert(user,doctor);
        }
        else if(userDto.getUserType() == UserType.PATIENT.getId()) {
            Patient patient = new Patient();
            patient.setInsuranceId(userDto.getPatientDetails().getInsuranceId());
            patient.setPcpId(userDto.getPatientDetails().getPcpId());
            patient.setPcpName(userDto.getPatientDetails().getPcpName());
            patient.setUserId(user.getId());
            patient.setMedicalHistoryFilePath(userDto.getPatientDetails().getMedicalHistoryFilePath());

            patient = patientDao.save(patient);

            return userConverter.convert(user,patient);
        }
        else
                return userConverter.convert(user);

    }




    public void validate(UserDto userDto) throws Exception
    {
        if(userDto.getUserType() == null)
            throw new Exception("UserType Required!");

        if(userDto.getUserType() == null || userDto.getContactNumber() == null || userDto.getUsername() == null)
            throw new Exception("User Type / Contact number / Username cannot be Null!");

        User user = userDao.findByUsernameOrContactNumber(userDto.getUsername() , userDto.getContactNumber());
        if(user != null)
            throw new Exception("User with given Username or Contact Number already Exists!");
    }


    public UserDto getUserDetails(UserRequestDto userRequestDto)
    {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(entityManager.getMetamodel().entity(User.class));


        criteriaQuery.select(userRoot);

        List<Predicate> predicates = new ArrayList<>();

        if (userRequestDto.getId() != null)
            predicates.add(criteriaBuilder.equal(userRoot.get("id"), userRequestDto.getId()));

        if (userRequestDto.getUsername() != null)
            predicates.add(criteriaBuilder.equal(userRoot.get("username"), userRequestDto.getUsername()));

        if (userRequestDto.getContactNumber() != null)
            predicates.add(criteriaBuilder.equal(userRoot.get("contactNumber"), userRequestDto.getContactNumber()));

        if (userRequestDto.getEmail() != null)
            predicates.add(criteriaBuilder.equal(userRoot.get("email"), userRequestDto.getEmail()));

        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));

        TypedQuery<User> query = entityManager.createQuery(criteriaQuery);
        User user =  query.getSingleResult();

        return userConverter.convert(user);
    }

    public List<UserDto> getUsersByType(Integer userType , User currentUser) throws Exception
    {
        List<User> userList = userDao.findAllByUserType(userType);
        List<UserDto> userDtoList = userConverter.convert(userList);

        List<Integer> userIds = userDtoList.stream().map(UserDto::getId).collect(Collectors.toList());
        if(userType == UserType.DOCTOR.getId())
        {
            List<Doctor> doctors = doctorDao.findAllByUserIdIn(userIds);
            HashMap<Integer , Doctor> map = new HashMap<>();

            for(Doctor doctor : doctors)
                map.put(doctor.getUserId() , doctor);

            for(UserDto userDto : userDtoList)
            {
                Doctor doctor = map.get(userDto.getId());
                DoctorDto doctorDto = doctorConverter.convertEntityToModel(doctor);
                userDto.setDoctorDetails(doctorDto);
            }
        }
        else if(userType == UserType.PATIENT.getId())
        {
            List<Patient> patients = patientDao.findAllByUserIdIn(userIds);
            HashMap<Integer , Patient> map = new HashMap<>();

            for(Patient patient : patients)
                map.put(patient.getUserId() , patient);

            for(UserDto userDto : userDtoList)
            {
                if(doctorService.checkAccess(currentUser.getId() , userDto.getId()) == true)
                {
                    Patient patient = patientDao.findByUserId(userDto.getId());
                    PatientDto patientDto = patientConverter.convert(patient);
                    userDto.setPatientDetails(patientDto);
                }
                else {
                    log.info("You don't have permission to access this User's Medical History!");
                }
            }
        }
        return userDtoList;
    }

    public UserDto getDetails(UserRequestDto userRequestDto , User currentUser) throws Exception
    {
        UserDto userDto = getUserDetails(userRequestDto);
        if(userDto.getUserType() != null) {
            if (userDto.getUserType() == UserType.DOCTOR.getId()) {
                Doctor doctor = doctorDao.findByUserId(userDto.getId());
                DoctorDto doctorDto = doctorConverter.convertEntityToModel(doctor);
                userDto.setDoctorDetails(doctorDto);
            } else if (userDto.getUserType() == UserType.PATIENT.getId())
            {
                if(doctorService.checkAccess(currentUser.getId() , userRequestDto.getId()) == true)
                {
                    Patient patient = patientDao.findByUserId(userDto.getId());
                    PatientDto patientDto = patientConverter.convert(patient);
                    userDto.setPatientDetails(patientDto);
                }
                else {
                   log.info("You don't have permission to access this User's Medical History!");
                }
            }
        }

        return userDto;
    }

}
