package com.salon.booking.domain.page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    public <V> Page<V> map(Function<? super E, ? extends V> mapper) {
        return new Page<>(items.stream()
                .map(mapper)
                .collect(Collectors.toList()), properties, totalItems);
    }

    @Override
    public String toString() {
        return "Page{" +
                "items=" + items +
                ", properties=" + properties +
                ", totalItems=" + totalItems +
                ", totalPages=" + totalPages +
                '}';
    }

    private static long ceilDivision(long number, long divisor) {
        if (divisor == 0) {
            return 0;
        }

        return (number + divisor - 1) / divisor;
    }
}
