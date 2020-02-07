package com.epam.bookingservice.service;

import com.epam.bookingservice.domain.Order;
import com.epam.bookingservice.domain.Timeslot;
import com.epam.bookingservice.domain.Timetable;

import java.time.LocalDate;
import java.util.List;

public interface TimeslotService {

    List<Timetable> findAllBetween(LocalDate fromInclusive, LocalDate toExclusive);

    void save(Timeslot timeslot, Order order);
}
