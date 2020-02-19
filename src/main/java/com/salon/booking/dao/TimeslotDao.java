package com.salon.booking.dao;

import com.salon.booking.entity.TimeslotEntity;

import java.time.LocalDate;
import java.util.List;

public interface TimeslotDao extends CrudDao<TimeslotEntity> {

    List<TimeslotEntity> findAllBetweenDatesSorted(LocalDate from, LocalDate to);

    void updateOrderId(Integer timeslotId, Integer orderId);

    List<TimeslotEntity> findAllTimeslotsOfTheSameDaySorted(Integer timeslotId);
}
