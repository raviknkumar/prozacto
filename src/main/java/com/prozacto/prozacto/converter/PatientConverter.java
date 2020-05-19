package com.prozacto.prozacto.converter;

import com.prozacto.prozacto.Entity.User.Patient;
import com.prozacto.prozacto.model.PatientDto;
import org.springframework.stereotype.Service;

@Service
public class PatientConverter {

    public PatientDto convert(Patient patient)
    {
        PatientDto patientDto = new PatientDto();
        patientDto.setMedicalHistoryFilePath(patient.getMedicalHistoryFilePath());
        patientDto.setPcpName(patient.getPcpName());
        patientDto.setPcpId(patient.getPcpId());
        patientDto.setInsuranceId(patient.getInsuranceId());
        patientDto.setUserId(patient.getUserId());
        return patientDto;
    }
}
