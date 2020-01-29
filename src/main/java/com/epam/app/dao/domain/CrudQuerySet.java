package com.epam.app.dao.domain;

public class CrudQuerySet {

    private final String findByIdQuery;
    private final String findAllQuery;
    private final String saveQuery;
    private final String updateQuery;
    private final String deleteByIdQuery;
    private final String countQuery;

    public CrudQuerySet(String findByIdQuery, String findAllQuery, String saveQuery, String updateQuery,
                        String deleteByIdQuery, String countQuery) {
        this.findByIdQuery = findByIdQuery;
        this.findAllQuery = findAllQuery;
        this.saveQuery = saveQuery;
        this.updateQuery = updateQuery;
        this.deleteByIdQuery = deleteByIdQuery;
        this.countQuery = countQuery;
    }

    public String getFindByIdQuery() {
        return findByIdQuery;
    }

    public String getFindAllQuery() {
        return findAllQuery;
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

    public String getCountQuery() {
        return countQuery;
    }
}
