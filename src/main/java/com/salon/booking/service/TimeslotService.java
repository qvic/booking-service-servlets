package com.salon.booking.service;

import com.salon.booking.domain.Service;
import com.salon.booking.domain.Timeslot;
import com.salon.booking.domain.Timetable;
import com.salon.booking.domain.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TimeslotService {

    List<Timetable> findAllBetween(LocalDate fromInclusive, LocalDate toExclusive);

    List<Timetable> findAllBetweenForWorker(Integer workerId, LocalDate fromInclusive, LocalDate toExclusive);

    List<Timetable> findTimetablesForOrderWith(Integer serviceId, Integer workerId, LocalDate currentDate);

    List<Timeslot> findTimeslotsForOrderWith(Integer selectedTimeslotId, Service service, User worker);

    void saveOrderTimeslot(Integer timeslotId, Integer orderId);

    Optional<Timeslot> findById(Integer id);
}
