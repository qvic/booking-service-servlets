package com.epam.bookingservice.entity;

public class Review {

    private final Integer id;
    private final String text;
    private final ReviewStatus status;

    public Review(Integer id, String text, ReviewStatus status) {
        this.id = id;
        this.text = text;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public ReviewStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", status=" + status +
                '}';
    }
}
