package com.prozacto.prozacto.Controller;

import com.prozacto.prozacto.Entity.User.Doctor;
import com.prozacto.prozacto.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    DoctorService doctorService;

    @PostMapping
    public Doctor addDoctor(@RequestBody Doctor doctor){
        return doctorService.addDoctor(doctor);
    }

    @GetMapping("/filter")
    public List<Doctor> findByName(@RequestParam("name") String name){
        return doctorService.findByName(name);
    }
}
