package com.epam.app.entity;

public class Review {

    private Integer id;
    private String text;

    public Review(Integer id, String text) {
        this.id = id;
        this.text = text;
    }

    public Integer getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Review setId(Integer id) {
        this.id = id;
        return this;
    }

    public Review setText(String text) {
        this.text = text;
        return this;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}
