package com.prozacto.prozacto.model;

import com.prozacto.prozacto.Entity.Appointment;
import com.prozacto.prozacto.model.enums.AppointmentStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDto {

    private Integer id;
    private Integer patientId;
    private Integer doctorId;    
    private Integer clinicId;    
    private String date;
    private Integer shiftId;
    private AppointmentStatusEnum status;
}
