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
@Entity(name = "timingShift")
@Where(clause = "deleted = 0")
public class TimingShift extends BaseEntityIntID{

    private Integer enrollmentId;

    private String fromTime;

    private String toTime;
}
