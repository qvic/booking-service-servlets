package com.epam.bookingservice.domain;

import java.time.LocalDate;
import java.util.List;

public class Timetable {

    private final LocalDate date;
    private final List<TimetableRow> rows;

    public Timetable(LocalDate date, List<TimetableRow> rows) {
        this.date = date;
        this.rows = rows;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<TimetableRow> getRows() {
        return rows;
    }
}
