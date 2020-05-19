package com.prozacto.prozacto.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "enrollment")
@Where(clause = "deleted = 0")
public class Enrollment extends BaseEntityIntID{

    private Integer doctorId;

    private Integer clinicId;

    private Double consultationFee;

    private Integer numOfPatients;
}
