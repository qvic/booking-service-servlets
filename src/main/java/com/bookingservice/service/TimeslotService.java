package com.bookingservice.service;

import com.bookingservice.domain.Timeslot;
import com.bookingservice.domain.Timetable;

import java.time.LocalDate;
import java.util.List;

public interface TimeslotService {

    List<Timetable> findAllBetween(LocalDate fromInclusive, LocalDate toExclusive);

    List<Timeslot> findConsecutiveFreeTimeslots(Integer startingTimeslotId);
}
