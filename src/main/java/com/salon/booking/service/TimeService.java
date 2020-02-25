package com.salon.booking.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface TimeService {

    LocalDate getCurrentDate();

    LocalDateTime getCurrentDateTime();
}
