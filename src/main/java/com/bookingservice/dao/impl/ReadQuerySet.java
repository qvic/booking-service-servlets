package com.bookingservice.dao.impl;

public class ReadQuerySet {

    private final String findByIdQuery;
    private final String findAllQuery;
    private final String countQuery;

    public ReadQuerySet(String findByIdQuery, String findAllQuery, String countQuery) {
        this.findByIdQuery = findByIdQuery;
        this.findAllQuery = findAllQuery;
        this.countQuery = countQuery;
    }

    public String getFindByIdQuery() {
        return findByIdQuery;
    }

    public String getFindAllQuery() {
        return findAllQuery;
    }

    public String getCountQuery() {
        return countQuery;
    }
}
