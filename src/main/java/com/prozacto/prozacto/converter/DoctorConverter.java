package com.prozacto.prozacto.converter;

import com.prozacto.prozacto.Entity.User.Doctor;
import com.prozacto.prozacto.Entity.User.User;
import com.prozacto.prozacto.model.DoctorDto;
import com.prozacto.prozacto.model.UserDto;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorConverter implements BaseConverter<Doctor, DoctorDto> {

    @Override
    public Doctor convertModelToEntity(DoctorDto model) {
        Doctor doctor = Doctor.builder()
                .specialization(model.getSpecialization())
                .subSpecialization(model.getSubSpecialization())
                .build();
        doctor.setId(model.getUserId());
        return doctor;
    }

    @Override
    public DoctorDto convertEntityToModel(Doctor entity) {
        return DoctorDto.builder()
                .specialization(entity.getSpecialization())
                .subSpecialization(entity.getSubSpecialization())
                .userId(entity.getUserId())
                .build();
    }

    @Override
    public List<Doctor> convertModelToEntity(List<DoctorDto> modelList) {
        if(CollectionUtils.isEmpty(modelList))
            return new ArrayList<>();
        return modelList.stream().map(m -> convertModelToEntity(m)).collect(Collectors.toList());
    }

    @Override
    public List<DoctorDto> convertEntityToModel(List<Doctor> entityList) {
        if(CollectionUtils.isEmpty(entityList))
            return new ArrayList<>();
        return entityList.stream().map(this::convertEntityToModel).collect(Collectors.toList());
    }

    @Override
    public void applyChanges(Doctor entity, DoctorDto Model) {

    }
}
