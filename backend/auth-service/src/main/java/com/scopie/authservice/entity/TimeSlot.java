package com.scopie.authservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_timeslot")
public class TimeSlot {

    public enum Day {
        Weekday,
        Sunday,
        Satuarday
    }

    @Id
    @Column(name = "slot_id")
    private long slotId;

    @Column(name = "startTime", nullable = false)
    private Time startTime;

    @Column(name = "endTime")
    private Time endTime;

    @Enumerated(EnumType.STRING)
    public Day day;

    @OneToMany(mappedBy = "slotId", fetch = FetchType.EAGER)
    private List<MovieTime> movieTimes;

}
