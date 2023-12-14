package com.scopie.authservice.repository;

import com.scopie.authservice.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Time;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
    @Query( value = "SELECT start_time FROM t_timeslot WHERE slot_id = ?1", nativeQuery = true )
    Time findTimeById(long thisItemTimeSlotId);
}
