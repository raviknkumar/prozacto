package com.prozacto.prozacto.Controller;


import com.prozacto.prozacto.model.ClinicDto;

import com.prozacto.prozacto.service.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clinic")
public class ClinicController {

    @Autowired
    ClinicService clinicService;

    @PostMapping("")
    public ClinicDto addClinic(@RequestBody ClinicDto clinicDto) throws Exception{
        return clinicService.create(clinicDto);
    }
}
