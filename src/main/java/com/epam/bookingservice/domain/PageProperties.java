package com.epam.bookingservice.domain;

import static com.epam.bookingservice.utility.ParseUtility.parseLongOrDefault;

public class PageProperties {

    private static final long DEFAULT_PAGE_NUMBER = 0;

    private long pageNumber;
    private long itemsPerPage;

    public PageProperties(long pageNumber, long itemsPerPage) {
        this.pageNumber = getValidatedPageNumber(pageNumber);
        this.itemsPerPage = getValidatedItemsPerPage(itemsPerPage);
    }

    public long getPageNumber() {
        return pageNumber;
    }

    public long getItemsPerPage() {
        return itemsPerPage;
    }

    public long getOffset() {
        return itemsPerPage * pageNumber;
    }

    private long getUpperBound() {
        return getOffset() + itemsPerPage - 1;
    }

    private long getValidatedPageNumber(long pageNumber) {
        return Math.max(pageNumber, 0);
    }

    private long getValidatedItemsPerPage(long itemsPerPage) {
        return Math.max(itemsPerPage, 0);
    }

    @Override
    public String toString() {
        return String.format("Page %d (%d - %d)", pageNumber, getOffset(), getUpperBound());
    }

    public static PageProperties buildByParameters(String pageNumber, String itemsPerPage, long defaultItemsPerPage) {
        long page = parseLongOrDefault(pageNumber, DEFAULT_PAGE_NUMBER);
        long items = parseLongOrDefault(itemsPerPage, defaultItemsPerPage);

        return new PageProperties(page, items);
    }
}
