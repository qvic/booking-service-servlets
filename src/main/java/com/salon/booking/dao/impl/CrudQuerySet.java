package com.salon.booking.dao.impl;

public class CrudQuerySet extends ReadQuerySet {

    private final String saveQuery;
    private final String updateQuery;
    private final String deleteByIdQuery;

    public CrudQuerySet(String findByIdQuery, String findAllQuery, String saveQuery, String updateQuery,
                        String deleteByIdQuery, String countQuery) {
        super(findByIdQuery, findAllQuery, countQuery);
        this.saveQuery = saveQuery;
        this.updateQuery = updateQuery;
        this.deleteByIdQuery = deleteByIdQuery;
    }

    public String getSaveQuery() {
        return saveQuery;
    }

    public String getUpdateQuery() {
        return updateQuery;
    }

    public String getDeleteByIdQuery() {
        return deleteByIdQuery;
    }
}
