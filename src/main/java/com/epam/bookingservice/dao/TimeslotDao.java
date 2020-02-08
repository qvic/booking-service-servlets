package com.epam.bookingservice.dao;

import com.epam.bookingservice.entity.TimeslotEntity;

import java.time.LocalDate;
import java.util.List;

public interface TimeslotDao extends CrudDao<TimeslotEntity> {

    List<TimeslotEntity> findAllBetween(LocalDate from, LocalDate to);

    void saveOrderAndUpdateTimeslot(TimeslotEntity timeslotEntity);
}
