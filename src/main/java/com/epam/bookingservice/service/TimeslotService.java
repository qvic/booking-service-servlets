package com.epam.bookingservice.service;

import com.epam.bookingservice.domain.Timetable;

import java.time.LocalDate;
import java.util.List;

public interface TimeslotService {

    List<Timetable> findAllFromNowToDate(LocalDate date);
}
