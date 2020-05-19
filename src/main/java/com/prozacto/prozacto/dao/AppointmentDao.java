package com.prozacto.prozacto.dao;

import com.prozacto.prozacto.Entity.Appointment;
import com.prozacto.prozacto.model.enums.AppointmentStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

public interface AppointmentDao extends JpaRepository<Appointment, Integer> {

    List<Appointment> findAllByPatientId(Integer patientId);
    List<Appointment> findAllByPatientIdAndDate(Integer patientId, Date date);
    List<Appointment> findAllByPatientIdAndDateAndStatusNot(Integer patientId, String date, AppointmentStatusEnum status);
    List<Appointment> findAllByPatientIdAndDateAndClinicIdAndStatusNot(Integer patientId, String date, Integer clinicId, AppointmentStatusEnum status);
}
