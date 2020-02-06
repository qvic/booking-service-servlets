package com.epam.bookingservice.dao;

import com.epam.bookingservice.entity.OrderEntity;

import java.util.List;

public interface OrderDao extends PageableCrudDao<OrderEntity> {

    List<OrderEntity> findAllByClientId(Integer id);

    List<OrderEntity> findAllByWorkerId(Integer id);
}
