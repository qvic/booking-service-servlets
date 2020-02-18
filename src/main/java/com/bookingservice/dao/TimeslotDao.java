package com.bookingservice.dao;

import com.bookingservice.entity.TimeslotEntity;

import java.time.LocalDate;
import java.util.List;

public interface TimeslotDao extends CrudDao<TimeslotEntity> {

    List<TimeslotEntity> findAllBetween(LocalDate from, LocalDate to);

    void updateOrderId(Integer timeslotId, Integer orderId);

    List<TimeslotEntity> findConsecutiveFreeTimeslots(Integer timeslotId);
}
