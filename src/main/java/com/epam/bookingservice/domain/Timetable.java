package com.epam.bookingservice.domain;

import com.epam.bookingservice.entity.TimeslotEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

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

    public static List<Timetable> fromTimeslots(List<TimeslotEntity> timeslots) {
        List<Timetable> timetables = new ArrayList<>();
        Map<LocalDate, List<TimeslotEntity>> grouped = timeslots.stream()
                .collect(Collectors.groupingBy(TimeslotEntity::getDate, TreeMap::new, toList()));

        for (Map.Entry<LocalDate, List<TimeslotEntity>> entry : grouped.entrySet()) {
            List<TimeslotEntity> timeslotEntities = entry.getValue();

            timetables.add(new Timetable(entry.getKey(), timeslotEntities.stream()
                    .map(TimetableRow::fromTimeslotEntity)
                    .collect(toList())));
        }

        return timetables;
    }
}
