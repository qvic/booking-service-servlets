package com.epam.bookingservice.dao;

import com.epam.bookingservice.entity.Service;

import java.util.List;

public interface ServiceDao extends CrudDao<Service> {

    List<Service> findAllByOrderId(Integer orderId);
}
