package com.epam.bookingservice.dao;

import com.epam.bookingservice.entity.OrderEntity;

import java.util.List;
import java.util.Optional;

public interface OrderDao extends PageableCrudDao<OrderEntity> {

    List<OrderEntity> findAllByClientId(Integer id);

    List<OrderEntity> findAllByWorkerId(Integer id);
}
