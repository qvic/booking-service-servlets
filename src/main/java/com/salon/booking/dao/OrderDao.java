package com.salon.booking.dao;

import com.salon.booking.entity.OrderEntity;

import java.util.List;

public interface OrderDao extends PageableCrudDao<OrderEntity> {

    List<OrderEntity> findAllByClientId(Integer id); /// todo pagination

    List<OrderEntity> findAllByWorkerId(Integer id);
}
