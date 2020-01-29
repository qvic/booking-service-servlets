package com.epam.bookingservice.dao;

import com.epam.bookingservice.entity.Appointment;

import java.util.Optional;

public interface AppointmentDao extends PageableCrudDao<Appointment> {

    Optional<Appointment> findAppointmentByReview(Integer reviewId);
}
