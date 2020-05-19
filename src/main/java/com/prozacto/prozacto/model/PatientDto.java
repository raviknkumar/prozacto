package com.prozacto.prozacto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatientDto {

    private Integer userId;
    private Integer insuranceId;
    private String pcpName;
    private Integer pcpId;
    private String medicalHistoryFilePath;
}
