package com.salon.booking.service;

import com.salon.booking.domain.Service;
import com.salon.booking.domain.Timeslot;
import com.salon.booking.domain.Timetable;
import com.salon.booking.domain.User;

import java.time.LocalDate;
import java.util.List;

public interface TimeslotService {

    List<Timetable> findAllBetween(LocalDate fromInclusive, LocalDate toExclusive);

    List<Timetable> findTimetablesForServiceWithWorker(Integer serviceId, Integer workerId);

    List<Timeslot> findTimeslotsForOrder(Integer selectedTimeslotId, Service service, User worker);

    void saveOrderTimeslot(Integer timeslotId, Integer orderId);
}
