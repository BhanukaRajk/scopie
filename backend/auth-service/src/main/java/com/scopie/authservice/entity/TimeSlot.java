package com.scopie.authservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_timeslot")
public class TimeSlot {

    @Id
    @Column(name = "slot_id")
    private Long slotId;

    @Column(name = "startTime", nullable = false)
    private Time startTime;

    @Column(name = "endTime")
    private Time endTime;

    private enum date{
        Weekday,
        Saturday,
        Sunday
    }
}
