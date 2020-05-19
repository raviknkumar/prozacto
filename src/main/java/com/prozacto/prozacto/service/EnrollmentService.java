package com.prozacto.prozacto.service;

import com.prozacto.prozacto.Entity.Enrollment;
import com.prozacto.prozacto.Entity.TimingShift;
import com.prozacto.prozacto.dao.EnrollmentDao;
import com.prozacto.prozacto.dao.TimingShiftDao;
import com.prozacto.prozacto.model.EnrollmentDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Doctor Enrolls with a clinic
 */
@Service
@Slf4j
public class EnrollmentService {

    @Autowired EnrollmentDao enrollmentDao;

    @Autowired
    TimingShiftDao timingShiftDao;

    public Enrollment findById(Integer id) throws Exception {
        if (id == null || id == 0) {
            throw new Exception("Id cannot be null or zero");
        }
        Optional<Enrollment> enrollmentOptional = enrollmentDao.findById(id);
        if (!enrollmentOptional.isPresent()) {
            throw new Exception("Enrollment not present");
        }

        return enrollmentOptional.get();
    }

    public Enrollment enrollDoctor(EnrollmentDto enrollmentDto) throws Exception {

//        validate(enrollmentDto.getTimingShifts()); // Validate the new shifts
//        validate(enrollmentDto); // Check For OverLapping Shifts with existing shifts if any.

        Enrollment enrollment = Enrollment.builder()
                                .clinicId(enrollmentDto.getClinicId())
                                .doctorId(enrollmentDto.getDoctorId())
                                .consultationFee(enrollmentDto.getConsultationFee())
                                .numOfPatients(enrollmentDto.getNumOfPatients())
                                .build();
        enrollment = enrollmentDao.save(enrollment);
        List<TimingShift> timingShiftList = enrollmentDto.getTimingShifts();
        for (TimingShift timingShift : timingShiftList) {
            timingShift.setEnrollmentId(enrollment.getId());
        }
        timingShiftDao.saveAll(timingShiftList);
        return enrollment;
    }

    public List<Enrollment> findAllByClinicId(Integer clinicId) throws Exception {
        return enrollmentDao.findAllByClinicId(clinicId);
    }

    public boolean validate(EnrollmentDto enrollmentDto) throws Exception
    {
        List<Enrollment> enrollments = enrollmentDao.findAllByDoctorId(enrollmentDto.getDoctorId());

        List<Integer> enrollmentIds = new ArrayList<>();
        for(Enrollment enrollment : enrollments)
            enrollmentIds.add(enrollment.getId());

        List<TimingShift> currentTimingShifts = timingShiftDao.findAllByEnrollmentIdIn(enrollmentIds);

        List<Interval> intervals = getIntervals(currentTimingShifts);

        for(TimingShift timingShift : enrollmentDto.getTimingShifts())
        {
            Integer startTime = Integer.parseInt(timingShift.getFromTime().replace(":" , ""));
            Integer endTime = Integer.parseInt(timingShift.getToTime().replace(":" , ""));
            for (Interval interval : intervals)
            {
                Integer start = interval.getStart();
                Integer end = interval.getEnd();
                if(!(start <= startTime && end <= startTime) || (start >= endTime && end >= endTime))
                    throw new Exception("Overlapping Shifts found!");
            }
        }

        return true;
    }



    public boolean validate(List<TimingShift> timingShifts) throws Exception
    {
        for(TimingShift timingShift : timingShifts)
        {
            Integer startTime = Integer.parseInt(timingShift.getFromTime().replace(":" , ""));
            Integer endTime = Integer.parseInt(timingShift.getToTime().replace(":" , ""));
            if(startTime > endTime)
                throw new Exception("Shifts are Wrong(Start time should be less than end time)!");
        }

       List<Interval> intervals = getIntervals(timingShifts);

        for(int i = 0 ; i < intervals.size()-1 ; i++)
        {

            Integer currStart = intervals.get(i).getStart();
            Integer currEnd = intervals.get(i).getEnd();
            Integer nextStart = intervals.get(i+1).getStart();
            Integer nextEnd = intervals.get(i+1).getEnd();
            if(!(currStart < nextStart && currEnd < nextEnd))
                throw new Exception("Overlapping Shifts found in Dto!");

        }

        return true;
    }

    public List<Interval> getIntervals(List<TimingShift> timingShifts)
    {
        List<Interval> intervals = new ArrayList<>();
        for(TimingShift timingShift : timingShifts)
        {
            Interval interval = new Interval();
            Integer startTime = Integer.parseInt(timingShift.getFromTime().replace(":" , ""));
            Integer endTime = Integer.parseInt(timingShift.getToTime().replace(":" , ""));
            interval.setStart(startTime);
            interval.setEnd(endTime);
            intervals.add(interval);
        }

        intervals.sort((m1, m2) -> {
            if (m1.getStart().equals(m2.getStart()))
                return m1.getEnd() - m2.getEnd();
            return m1.getStart() - m2.getStart();
        });
        return intervals;
    }

    public List<EnrollmentDto> findAllByDoctorId(Integer doctorId){
        List<Enrollment> enrollments = enrollmentDao.findAllByDoctorId(doctorId);
        return convertEntityToModel(enrollments);
    }

    public EnrollmentDto convertEntityToModel(Enrollment enrollment){
        if(enrollment == null)
            return null;

        return EnrollmentDto.builder()
                 .id(enrollment.getId())
                .clinicId(enrollment.getClinicId())
                .doctorId(enrollment.getDoctorId())
                .consultationFee(enrollment.getConsultationFee())
                .numOfPatients(enrollment.getNumOfPatients())
                .build();
    }

    public List<EnrollmentDto> convertEntityToModel(List<Enrollment> enrollments){
        if(CollectionUtils.isEmpty(enrollments))
            return Collections.emptyList();

        return enrollments.stream().map(this::convertEntityToModel).collect(Collectors.toList());
    }

}

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
class Interval {
    Integer start;
    Integer end;
}