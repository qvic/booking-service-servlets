package com.salon.booking.dao;

import com.salon.booking.entity.OrderEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderDao extends PageableCrudDao<OrderEntity> {

    List<OrderEntity> findAllByClientId(Integer id); /// todo pagination

    List<OrderEntity> findAllByWorkerId(Integer id);

    List<OrderEntity> findAllFinishedAfter(LocalDateTime date);
}
