package com.prozacto.prozacto.Controller;

import com.prozacto.prozacto.model.AppointmentDto;
import com.prozacto.prozacto.service.AppointmentService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;

    @GetMapping()
    public List<AppointmentDto> getAppointmentsByPatientId(@RequestParam("patientId") Integer patientId) throws NotFoundException {
        List<AppointmentDto> appointmentDtos = appointmentService.findAllByPatientId(patientId);
        if (CollectionUtils.isEmpty(appointmentDtos)){
            throw new NotFoundException("No Appointments Found For Patient Id : " + patientId);
        }
        return appointmentDtos;
    }

    @PostMapping()
    public AppointmentDto createAppointment(@RequestBody AppointmentDto appointmentDto) throws Exception {
        return appointmentService.createAppointment(appointmentDto);
    }

    @DeleteMapping(path="/{appointmentId}")
    public String deleteAppointment(@PathVariable("appointmentId") Integer appointmentId) throws Exception {
        appointmentService.deleteAppointment(appointmentId);
        return "Appointment Deleted Successfully!";

    }

    @PostMapping("/reschedule")
    public AppointmentDto rescheduleAppointment(@RequestBody AppointmentDto appointmentDto) throws Exception {
        return appointmentService.rescheduleAppointment(appointmentDto);
    }


}
