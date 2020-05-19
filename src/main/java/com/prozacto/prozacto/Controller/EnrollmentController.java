package com.prozacto.prozacto.Controller;

import com.prozacto.prozacto.Entity.Enrollment;
import com.prozacto.prozacto.Entity.User.User;
import com.prozacto.prozacto.model.EnrollmentDto;
import com.prozacto.prozacto.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enrollment")
public class EnrollmentController {

    @Autowired EnrollmentService enrollmentService;

    @GetMapping("/find")
    public Enrollment findById(@RequestParam("id") Integer id) throws Exception {
        return enrollmentService.findById(id);
    }

    @PostMapping("/doctor")
    public Enrollment enrollDoctor(@RequestBody EnrollmentDto enrollmentDto) throws Exception {
        return enrollmentService.enrollDoctor(enrollmentDto);
    }
}
