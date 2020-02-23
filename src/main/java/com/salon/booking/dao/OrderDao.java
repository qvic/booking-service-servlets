package com.salon.booking.dao;

import com.salon.booking.entity.OrderEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderDao extends PageableCrudDao<OrderEntity> {

    List<OrderEntity> findAllByClientId(Integer clientId);

    List<OrderEntity> findAllByWorkerId(Integer workerId);

    List<OrderEntity> findAllFinishedAfter(LocalDateTime date, Integer clientId);
}
