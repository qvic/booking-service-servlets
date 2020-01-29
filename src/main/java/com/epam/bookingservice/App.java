package com.epam.bookingservice;

import com.epam.bookingservice.dao.AppointmentDao;
import com.epam.bookingservice.dao.ReviewDao;
import com.epam.bookingservice.dao.UserDao;
import com.epam.bookingservice.dao.Page;
import com.epam.bookingservice.dao.PageProperties;
import com.epam.bookingservice.entity.Appointment;
import com.epam.bookingservice.entity.Review;
import com.epam.bookingservice.entity.Role;
import com.epam.bookingservice.entity.User;
import com.epam.bookingservice.context.ApplicationInjector;
import com.epam.bookingservice.service.UserService;

import java.util.List;
import java.util.Optional;

public class App {

    public static void main(String[] args) {
        ApplicationInjector injector = ApplicationInjector.getInstance();

        userDaoUsage(injector);
        pagingUsage(injector);
        appointmentDaoUsage(injector);
        reviewDaoUsage(injector);
    }

    private static void pagingUsage(ApplicationInjector injector) {
        UserService userService = injector.getUserService();
        Page<User> firstPage = userService.findAll(new PageProperties(0, 2));
        System.out.println(firstPage);
        Page<User> secondPage = userService.findAll(firstPage.nextPageProperties());
        System.out.println(secondPage);
    }

    private static void appointmentDaoUsage(ApplicationInjector injector) {
        AppointmentDao appointmentDao = injector.getAppointmentDao();
        Optional<Appointment> appointment = appointmentDao.findById(2);
        appointment.ifPresent(System.out::println);
        List<Appointment> appointments = appointmentDao.findAll();
        System.out.println(appointments);

        Optional<Appointment> appointmentByReview = appointmentDao.findAppointmentByReview(1);
        appointmentByReview.ifPresent(System.out::println);
    }

    private static void reviewDaoUsage(ApplicationInjector injector) {
        ReviewDao reviewDao = injector.getReviewDao();
        List<Review> reviewsByAppointment = reviewDao.findReviewsByAppointment(1);
        System.out.println(reviewsByAppointment);
    }

    private static void userDaoUsage(ApplicationInjector injector) {
        UserDao dao = injector.getUserDao();
        User build = User.builder()
                .setName("new user")
                .setEmail("email123")
                .setRole(new Role(1, "CLIENT"))
                .setPassword("password")
                .build();
        System.out.println(dao.count());
//        User saved = dao.save(build);
//        System.out.println(build);
//        System.out.println(saved);
//        System.out.println(dao.count());
//
//        saved.setEmail("some new email");
//        dao.update(saved);
//        System.out.println(dao.findById(saved.getId()));

        System.out.println(dao.findAllByRoleName("CLIENT"));
    }
}
