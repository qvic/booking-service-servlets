package com.epam.bookingservice.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

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

    @Override
    public String toString() {
        return "Timetable{" +
                "date=" + date +
                ", rows=" + rows +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Timetable timetable = (Timetable) o;
        return Objects.equals(date, timetable.date) &&
                Objects.equals(rows, timetable.rows);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, rows);
    }
}
