package com.epam.bookingservice.domain;

import java.time.LocalDate;
import java.util.List;

public class Timetable {

    private final LocalDate date;
    private final List<Timeslot> rows;

    public Timetable(LocalDate date, List<Timeslot> rows) {
        this.date = date;
        this.rows = rows;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<Timeslot> getRows() {
        return rows;
    }
}
