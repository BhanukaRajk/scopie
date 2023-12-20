package com.scopie.authservice.seeds;

import com.scopie.authservice.entity.TimeSlot;
import com.scopie.authservice.repository.TimeSlotRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Arrays;

@Component
@Slf4j
public class TableDataLoader implements CommandLineRunner {

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Override
    public void run(String... args) throws Exception {
        // Check if data already exists
        if (timeSlotRepository.count() == 0) {
            // Add sample data
            TimeSlot timeSlot1 = TimeSlot.builder()
                    .slotId(1)
                    .startTime(Time.valueOf(LocalTime.parse("09:00")))
                    .endTime(Time.valueOf(LocalTime.parse("12:00")))
                    .day(TimeSlot.Day.Weekday)
                    .build();

            TimeSlot timeSlot2 = TimeSlot.builder()
                    .slotId(2)
                    .startTime(Time.valueOf(LocalTime.parse("14:00")))
                    .endTime(Time.valueOf(LocalTime.parse("17:00")))
                    .day(TimeSlot.Day.Sunday)
                    .build();

            // Save data to the database
            timeSlotRepository.saveAll(Arrays.asList(timeSlot1, timeSlot2));

            log.info("Sample data added to t_timeslot table.");
        } else {
            log.info("Data already exists in t_timeslot table.");
        }
    }
}