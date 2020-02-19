package com.salon.booking.service;

import com.salon.booking.domain.Timeslot;
import com.salon.booking.domain.Timetable;
import com.salon.booking.entity.TimeslotEntity;

import java.time.LocalDate;
import java.util.List;

public interface TimeslotService {

    List<Timetable> findAllBetween(LocalDate fromInclusive, LocalDate toExclusive);

    List<Timeslot> findConsecutiveFreeTimeslots(Integer startingTimeslotId);

    void update(Timeslot timeslot);
}
