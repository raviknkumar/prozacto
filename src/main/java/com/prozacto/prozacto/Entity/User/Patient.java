package com.prozacto.prozacto.Entity.User;

import com.prozacto.prozacto.Entity.BaseEntityIntID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "Patient")
@Where(clause = "deleted = 0")
public class Patient extends BaseEntityIntID {

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "insurance_id")
    private Integer insuranceId;

    @Column(name = "pcp_name")
    private String pcpName;

    @Column(name = "pcp_id")
    private Integer pcpId;

    @Column(name = "medical_history_file_path")
    private String medicalHistoryFilePath;

}
