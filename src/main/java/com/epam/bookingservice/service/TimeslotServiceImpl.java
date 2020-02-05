package com.epam.bookingservice.service;

import com.epam.bookingservice.dao.OrderDao;
import com.epam.bookingservice.dao.TimeslotDao;
import com.epam.bookingservice.domain.Timetable;
import com.epam.bookingservice.entity.TimeslotEntity;

import java.time.LocalDate;
import java.util.List;

public class TimeslotServiceImpl implements TimeslotService {

    private final TimeslotDao timeslotDao;
    private final OrderDao orderDao;

    public TimeslotServiceImpl(TimeslotDao timeslotDao, OrderDao orderDao) {
        this.timeslotDao = timeslotDao;
        this.orderDao = orderDao;
    }

    @Override
    public List<Timetable> findAllFromNowToDate(LocalDate date) {
        List<TimeslotEntity> timeslots = timeslotDao.findAllFromNowToDate(date);
        return Timetable.fromTimeslots(timeslots);
    }
}
