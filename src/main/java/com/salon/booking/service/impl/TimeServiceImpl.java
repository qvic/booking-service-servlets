package com.salon.booking.service.impl;

import com.salon.booking.service.TimeService;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TimeServiceImpl implements TimeService {

    @Override
    public LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    @Override
    public LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }
}
