package com.epam.app.dao;

import com.epam.app.entity.Appointment;

import java.util.Optional;

public interface AppointmentDao extends PageableCrudDao<Appointment> {

    Optional<Appointment> findAppointmentByReview(Integer reviewId);
}
