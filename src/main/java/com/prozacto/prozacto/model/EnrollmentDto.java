package com.prozacto.prozacto.model;

import com.prozacto.prozacto.Entity.TimingShift;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentDto {

    private Integer id;
    private Integer doctorId;
    private Integer clinicId;
    private Double consultationFee;
    private Integer numOfPatients;
    private List<TimingShift> timingShifts;
}
