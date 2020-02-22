package com.salon.booking.dao;

import com.salon.booking.entity.TimeslotEntity;

import java.time.LocalDate;
import java.util.List;

public interface TimeslotDao extends CrudDao<TimeslotEntity> {

    List<TimeslotEntity> findAllBetweenDatesSorted(LocalDate from, LocalDate to);

    List<TimeslotEntity> findSameDayTimeslotsSorted(Integer timeslotId);

    void saveOrderTimeslot(Integer timeslotId, Integer orderId);
}