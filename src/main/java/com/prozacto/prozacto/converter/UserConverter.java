package com.prozacto.prozacto.converter;


import com.prozacto.prozacto.Entity.User.Doctor;
import com.prozacto.prozacto.Entity.User.Patient;
import com.prozacto.prozacto.Entity.User.User;


import com.prozacto.prozacto.model.DoctorDto;
import com.prozacto.prozacto.model.PatientDto;
import com.prozacto.prozacto.model.UserDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserConverter {


    public User convert(UserDto userDto)
    {
        User user = new User();
        user.setId(userDto.getId());
        user.setAge(userDto.getAge());
        user.setContactNumber(userDto.getContactNumber());
        user.setEmail(userDto.getEmail());
        user.setUserType(userDto.getUserType());
        user.setPassword(userDto.getPassword());
        user.setUsername(userDto.getUsername());
        return user;
    }

    public UserDto convert(User user , Doctor doctor)
    {
        UserDto userDto = convert(user);

        DoctorDto doctorDto = new DoctorDto();
        doctorDto.setUserId(user.getId());
        doctorDto.setSpecialization(doctor.getSpecialization());
        doctorDto.setSubSpecialization(doctor.getSubSpecialization());
        userDto.setDoctorDetails(doctorDto);

        return userDto;
    }

    public UserDto convert(User user)
    {
        UserDto userDto = new UserDto();
        userDto.setAge(user.getAge());
        userDto.setContactNumber(user.getContactNumber());
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setUserType(user.getUserType());

        return userDto;
    }

    public List<UserDto> convert(List<User> users)
    {
        List<UserDto> userDtoList = new ArrayList<>();
        for(User user : users)
        {
            UserDto userDto = convert(user);
            userDtoList.add(userDto);
        }

        return userDtoList;
    }

    public UserDto convert(User user , Patient patient)
    {
        UserDto userDto = convert(user);

        PatientDto patientDto = new PatientDto();
        patientDto.setInsuranceId(patient.getInsuranceId());
        patientDto.setPcpId(patient.getPcpId());
        patientDto.setPcpName(patient.getPcpName());
        patientDto.setUserId(patient.getUserId());
        patientDto.setMedicalHistoryFilePath(patient.getMedicalHistoryFilePath());

        userDto.setPatientDetails(patientDto);

        return userDto;
    }

}
