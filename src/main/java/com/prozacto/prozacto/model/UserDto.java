package com.prozacto.prozacto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Integer id;
    private String username;
    private String password;
    private String email;
    private String contactNumber;
    private Integer age;
    private Integer userType;

    private DoctorDto doctorDetails;
    private PatientDto patientDetails;

}
