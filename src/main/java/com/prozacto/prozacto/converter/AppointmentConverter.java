package com.prozacto.prozacto.converter;

import com.prozacto.prozacto.Entity.Appointment;
import com.prozacto.prozacto.model.AppointmentDto;
import com.prozacto.prozacto.model.enums.AppointmentStatusEnum;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AppointmentConverter implements BaseConverter<Appointment, AppointmentDto> {

    @Override
    public Appointment convertModelToEntity(AppointmentDto model) {

        if(model == null)
            return null;

        Appointment appointment = Appointment.builder()
                .patientId(model.getPatientId())
                .doctorId(model.getDoctorId())
                .clinicId(model.getClinicId())
                .date(model.getDate())
                .shiftId(model.getShiftId())
                .status(model.getStatus())
                .build();

        appointment.setId(model.getId());
        return appointment;
    }

    @Override
    public AppointmentDto convertEntityToModel(Appointment entity) {

        if(entity == null)
            return null;

        return AppointmentDto.builder()
                .id(entity.getId())
                .patientId(entity.getPatientId())
                .doctorId(entity.getDoctorId())
                .clinicId(entity.getClinicId())
                .date(entity.getDate())
                .shiftId(entity.getShiftId())
                .status(entity.getStatus())
                .build();

    }

    @Override
    public List<Appointment> convertModelToEntity(List<AppointmentDto> modelList) {
        if(CollectionUtils.isEmpty(modelList))
            return new ArrayList<>();
        return modelList.stream().map(this::convertModelToEntity).collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDto> convertEntityToModel(List<Appointment> entityList) {
        if(CollectionUtils.isEmpty(entityList))
            return Collections.emptyList();
        return entityList.stream().map(this::convertEntityToModel).collect(Collectors.toList());
    }

    @Override
    public void applyChanges(Appointment entity, AppointmentDto Model) {

    }
}
