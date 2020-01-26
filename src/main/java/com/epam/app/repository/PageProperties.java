package com.epam.app.repository;

public class PageProperties {

    private long pageNumber;
    private long itemsPerPage;

    public PageProperties(long pageNumber, long itemsPerPage) {
        this.pageNumber = getValidatedPageNumber(pageNumber);
        this.itemsPerPage = getValidatedItemsPerPage(itemsPerPage);
    }

    /**
     * @return pageNumber (starting from 0)
     */
    public long getPageNumber() {
        return pageNumber;
    }

    public long getItemsPerPage() {
        return itemsPerPage;
    }

    public long getLowerBound() {
        return itemsPerPage * pageNumber;
    }

    private long getUpperBound() {
        return getLowerBound() + itemsPerPage - 1;
    }

    private long getValidatedPageNumber(long pageNumber) {
        return Math.max(pageNumber, 0);
    }

    private long getValidatedItemsPerPage(long itemsPerPage) {
        return Math.max(itemsPerPage, 0);
    }

    @Override
    public String toString() {
        return String.format("Page %d (%d - %d)", pageNumber, getLowerBound(), getUpperBound());
    }
}
