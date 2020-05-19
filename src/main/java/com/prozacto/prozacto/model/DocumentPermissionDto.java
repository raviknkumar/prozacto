package com.prozacto.prozacto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentPermissionDto {

    private Integer id;
    private Integer doctorId;    
    private Integer patientId;    
    private String accessDate;    
    private Integer clinicId;
}
