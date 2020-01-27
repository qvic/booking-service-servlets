package com.epam.app.domain;

public class Review {

    private Integer id;
    private Appointment appointment;
    private String text;

    public Review(Integer id, Appointment appointment, String text) {
        this.id = id;
        this.appointment = appointment;
        this.text = text;
    }

    public Integer getId() {
        return id;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", appointment=" + appointment +
                ", text='" + text + '\'' +
                '}';
    }
}
