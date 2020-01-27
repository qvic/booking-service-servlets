package com.epam.app.dao;

import java.util.List;

public class Page<E> {

    private List<E> items;
    private PageProperties properties;
    private long totalItems;
    private long totalPages;

    public Page(List<E> items, PageProperties properties, long totalItems) {
        this.items = items;
        this.properties = properties;
        this.totalItems = totalItems;
        this.totalPages = ceilDivision(totalItems, properties.getItemsPerPage());
    }

    public long getTotalPages() {
        return totalPages;
    }

    public List<E> getItems() {
        return items;
    }

    public PageProperties getProperties() {
        return properties;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public PageProperties nextPageProperties() {
        return new PageProperties(properties.getPageNumber() + 1, properties.getItemsPerPage());
    }

    private static long ceilDivision(long number, long divisor) {
        return (number + divisor - 1) / divisor;
    }
}
