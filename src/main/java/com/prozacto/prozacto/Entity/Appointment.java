package com.prozacto.prozacto.Entity;

import com.prozacto.prozacto.model.enums.AppointmentStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "appointment")
@Where(clause = "deleted = false")
public class Appointment extends BaseEntityIntID {

    @Column(name = "patient_id")
    private Integer patientId;

    @Column(name = "doctor_id")
    private Integer doctorId;

    @Column(name = "clinic_id")
    private Integer clinicId;

    @Column(name = "date")
    private String date;

    @Column(name = "shift_id")
    private Integer shiftId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private AppointmentStatusEnum status;
}
