package com.prozacto.prozacto.converter;

import com.prozacto.prozacto.Entity.Clinic;
import com.prozacto.prozacto.Entity.User.User;
import com.prozacto.prozacto.model.ClinicDto;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClinicConverter implements BaseConverter<Clinic, ClinicDto>  {

    @Override
    public Clinic convertModelToEntity(ClinicDto model) {
        Clinic clinic = Clinic.builder()
                .name(model.getName())
                .locationId(model.getLocationId())
                .build();

        return clinic;
    }

    @Override
    public ClinicDto convertEntityToModel(Clinic entity) {
        return ClinicDto.builder()
                .name(entity.getName())
                .locationId(entity.getLocationId())
                .build();
    }

    @Override
    public List<Clinic> convertModelToEntity(List<ClinicDto> modelList) {
        if(CollectionUtils.isEmpty(modelList))
            return new ArrayList<>();
        return modelList.stream().map(m -> convertModelToEntity(m)).collect(Collectors.toList());
    }

    @Override
    public List<ClinicDto> convertEntityToModel(List<Clinic> entityList) {
        if(CollectionUtils.isEmpty(entityList))
            return new ArrayList<>();
        return entityList.stream().map(this::convertEntityToModel).collect(Collectors.toList());
    }

    @Override
    public void applyChanges(Clinic entity, ClinicDto Model) {

    }
}
