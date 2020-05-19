package com.prozacto.prozacto.Entity.User;

import com.prozacto.prozacto.Entity.BaseEntityIntID;
import com.prozacto.prozacto.model.SpecializationEnum;
import com.prozacto.prozacto.model.SubSpecializationEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "doctor")
@Where(clause = "deleted=0")
public class Doctor extends BaseEntityIntID {

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "specialization")
    @Enumerated(value = EnumType.STRING)
    private SpecializationEnum specialization;

    @Column(name = "sub_specialization")
    @Enumerated(value = EnumType.STRING)
    private SubSpecializationEnum subSpecialization;

}
