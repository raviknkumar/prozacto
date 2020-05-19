package com.prozacto.prozacto.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "document_permission")
@Where(clause = "deleted = 0")
public class DocumentPermission extends BaseEntityIntID {

    @Column(name = "doctor_id")
    private Integer doctorId;

    @Column(name = "patient_id")
    private Integer patientId;

    @Column(name = "access_date")
    private String accessDate;

    @Column(name = "clinic_id")
    private Integer clinicId;
}
