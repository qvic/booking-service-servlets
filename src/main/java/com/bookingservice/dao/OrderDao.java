package com.bookingservice.dao;

import com.bookingservice.entity.OrderEntity;

import java.util.List;

public interface OrderDao extends PageableCrudDao<OrderEntity> {

    List<OrderEntity> findAllByClientId(Integer id);

    List<OrderEntity> findAllByWorkerId(Integer id);
}
