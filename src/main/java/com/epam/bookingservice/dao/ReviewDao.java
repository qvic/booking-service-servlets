package com.epam.bookingservice.dao;

import com.epam.bookingservice.entity.Review;

import java.util.List;

public interface ReviewDao extends PageableCrudDao<Review> {

    List<Review> findReviewsByAppointment(Integer appointmentId);
}
