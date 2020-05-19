package com.prozacto.prozacto.service;

import com.prozacto.prozacto.Entity.Clinic;
import com.prozacto.prozacto.Entity.Location.Location;
import com.prozacto.prozacto.converter.ClinicConverter;
import com.prozacto.prozacto.converter.LocationConverter;
import com.prozacto.prozacto.dao.ClinicDao;
import com.prozacto.prozacto.dao.LocationDao;
import com.prozacto.prozacto.model.ClinicDto;
import com.prozacto.prozacto.model.LocationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClinicService {

    @Autowired private ClinicDao clinicDao;
    @Autowired private ClinicConverter clinicConverter;
    @Autowired private LocationDao locationDao;
    @Autowired private LocationConverter locationConverter;

    public ClinicDto getClinicById(Integer id)
    {
        Optional<Clinic> clinic = clinicDao.findById(id);
        if(clinic.isPresent())
            return clinicConverter.convertEntityToModel(clinic.get());

        return null;
    }

    public ClinicDto create(ClinicDto clinicDto)
    {
        Location location;
        LocationDto locationDto = new LocationDto();

        locationDto.setId(clinicDto.getLocationId());
        locationDto.setAddress(clinicDto.getLocationDto().getAddress());
        locationDto.setLandmark(clinicDto.getLocationDto().getLandmark());
        locationDto.setLatitude(clinicDto.getLocationDto().getLatitude());
        locationDto.setLongitude(clinicDto.getLocationDto().getLongitude());
        location = locationDao.save(locationConverter.convertModelToEntity(locationDto));
        locationDto = locationConverter.convertEntityToModel(location);

        clinicDto.setLocationId(locationDto.getId());
        Clinic clinic = clinicDao.save(clinicConverter.convertModelToEntity(clinicDto));
        clinicDto =  clinicConverter.convertEntityToModel(clinic);
        clinicDto.setLocationDto(locationDto);
        clinicDto.setLocationId(location.getId());

        return clinicDto;
    }
}
