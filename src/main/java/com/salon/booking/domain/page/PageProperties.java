package com.salon.booking.domain.page;

import java.util.Objects;

import static com.salon.booking.utility.ParseUtility.parseLongOrDefault;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageProperties that = (PageProperties) o;
        return pageNumber == that.pageNumber &&
                itemsPerPage == that.itemsPerPage;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageNumber, itemsPerPage);
    }

    public static PageProperties buildByParameters(String pageNumber, String itemsPerPage, long defaultItemsPerPage) {
        long page = parseLongOrDefault(pageNumber, DEFAULT_PAGE_NUMBER);
        long items = parseLongOrDefault(itemsPerPage, defaultItemsPerPage);

        return new PageProperties(page, items);
    }
}
