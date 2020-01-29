package com.epam.app.dao.domain;

public class PageableCrudQuerySet extends CrudQuerySet {

    private final String findAllPageableQuery;

    public PageableCrudQuerySet(String findByIdQuery, String findAllQuery, String saveQuery, String updateQuery,
                                String deleteByIdQuery, String countQuery, String findAllPageableQuery) {
        super(findByIdQuery, findAllQuery, saveQuery, updateQuery, deleteByIdQuery, countQuery);
        this.findAllPageableQuery = findAllPageableQuery;
    }

    public String getFindAllPageableQuery() {
        return findAllPageableQuery;
    }
}
