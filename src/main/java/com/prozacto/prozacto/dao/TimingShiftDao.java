package com.prozacto.prozacto.dao;

import com.prozacto.prozacto.Entity.TimingShift;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimingShiftDao extends JpaRepository<TimingShift, Integer> {
    List<TimingShift> findAllByEnrollmentIdIn(List<Integer> enrolmentId);
}
