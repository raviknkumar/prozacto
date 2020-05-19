package com.prozacto.prozacto.converter;

import com.prozacto.prozacto.Entity.DocumentPermission;
import com.prozacto.prozacto.model.DocumentPermissionDto;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentPermissionConverter implements BaseConverter<DocumentPermission, DocumentPermissionDto> {

    @Override
    public DocumentPermission convertModelToEntity(DocumentPermissionDto model) {

        if(model == null)
            return null;

        DocumentPermission documentPermission = DocumentPermission.builder()
                .patientId(model.getPatientId())
                .doctorId(model.getDoctorId())
                .clinicId(model.getClinicId())
                .accessDate(model.getAccessDate())
                .build();

        documentPermission.setId(model.getId());
        return documentPermission;
    }

    @Override
    public DocumentPermissionDto convertEntityToModel(DocumentPermission entity) {
        return DocumentPermissionDto.builder()
                .id(entity.getId())
                .patientId(entity.getPatientId())
                .doctorId(entity.getDoctorId())
                .clinicId(entity.getClinicId())
                .accessDate(entity.getAccessDate())
                .build();

    }

    @Override
    public List<DocumentPermission> convertModelToEntity(List<DocumentPermissionDto> modelList) {
        if(CollectionUtils.isEmpty(modelList))
            return new ArrayList<>();
        return modelList.stream().map(this::convertModelToEntity).collect(Collectors.toList());
    }

    @Override
    public List<DocumentPermissionDto> convertEntityToModel(List<DocumentPermission> entityList) {
        if(CollectionUtils.isEmpty(entityList))
            return Collections.emptyList();
        return entityList.stream().map(this::convertEntityToModel).collect(Collectors.toList());
    }

    @Override
    public void applyChanges(DocumentPermission entity, DocumentPermissionDto Model) {

    }
}
