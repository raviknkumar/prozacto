package com.prozacto.prozacto.service;

import com.prozacto.prozacto.Entity.Appointment;
import com.prozacto.prozacto.converter.AppointmentConverter;
import com.prozacto.prozacto.dao.AppointmentDao;
import com.prozacto.prozacto.model.AppointmentDto;
import com.prozacto.prozacto.model.DocumentPermissionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static com.prozacto.prozacto.model.enums.AppointmentStatusEnum.COMPLETED;
import static com.prozacto.prozacto.model.enums.AppointmentStatusEnum.PENDING;

@Service
public class AppointmentService {

    @Autowired
    AppointmentDao appointmentDao;
    @Autowired
    AppointmentConverter appointmentConverter;
    @Autowired DocumentPermissionService documentPermissionService;

    public List<AppointmentDto> findAllByPatientId(Integer patientId) {
        List<Appointment> appointments = appointmentDao.findAllByPatientId(patientId);
        return appointmentConverter.convertEntityToModel(appointments);
    }

    public AppointmentDto createAppointment(AppointmentDto appointmentDto) throws Exception {

        Integer patientId = appointmentDto.getPatientId();
        Integer clinicId = appointmentDto.getClinicId();
        appointmentDto.setStatus(PENDING);
        String date = appointmentDto.getDate();
        List<Appointment> appointments = appointmentDao.findAllByPatientIdAndDateAndClinicIdAndStatusNot(patientId, date, clinicId, COMPLETED);
        if(!CollectionUtils.isEmpty(appointments)){
            throw new Exception("You Cannot have more than 1 Active Appointments For Date: " + date);
        }

        Appointment appointment = appointmentConverter.convertModelToEntity(appointmentDto);
        DocumentPermissionDto documentPermissionDto = createPermissionDto(appointmentDto);
        documentPermissionService.grantAccess(documentPermissionDto);

        appointment = appointmentDao.save(appointment);
        return appointmentConverter.convertEntityToModel(appointment);
    }

    private DocumentPermissionDto createPermissionDto(AppointmentDto appointmentDto) {
        return DocumentPermissionDto.builder()
                .clinicId(appointmentDto.getClinicId())
                .doctorId(appointmentDto.getDoctorId())
                .accessDate(appointmentDto.getDate())
                .patientId(appointmentDto.getPatientId())
                .build();
    }

    public void deleteAppointment(Integer appointmentId) throws Exception {

        Appointment appointment = appointmentDao.findById(appointmentId).orElse(null);
        if(appointment == null){
            throw new Exception("No Appointment Exists with Id : "+ appointmentId);
        }
        appointment.setDeleted(true);
        appointment = appointmentDao.save(appointment);
        DocumentPermissionDto permissionDto = createPermissionDto(appointmentConverter.convertEntityToModel(appointment));
        documentPermissionService.deleteAccess(permissionDto);

    }

    public AppointmentDto rescheduleAppointment(AppointmentDto appointmentDto) throws Exception {
        Integer appointmentId = appointmentDto.getId();
        Appointment appointment = findIfExists(appointmentId);
        appointment.setDate(appointmentDto.getDate());
        appointment = appointmentDao.save(appointment);

        DocumentPermissionDto documentPermissionDto = createPermissionDto(appointmentDto);
        documentPermissionService.grantAccess(documentPermissionDto);

        return appointmentConverter.convertEntityToModel(appointment);
    }

    public Appointment findIfExists(Integer appointmentId) throws Exception {
        Appointment appointment = appointmentDao.findById(appointmentId).orElse(null);
        if(appointment == null){
            throw new Exception("No Appointment Exists with Id : "+ appointmentId);
        }
        return appointment;
    }
}
