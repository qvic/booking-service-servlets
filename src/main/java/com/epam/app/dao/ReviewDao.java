package com.epam.app.dao;

import com.epam.app.entity.Review;

import java.util.List;

public interface ReviewDao extends PageableCrudDao<Review> {

    List<Review> findReviewsByAppointment(Integer appointmentId);
}
