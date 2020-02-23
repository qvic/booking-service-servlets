package com.salon.booking.domain;

import java.util.Objects;

public class Notification {

    private final Integer id;
    private final Order order;
    private final Boolean read;

    public Notification(Integer id, Order order, Boolean read) {
        this.id = id;
        this.order = order;
        this.read = read;
    }

    public Integer getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public Boolean getRead() {
        return read;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", order=" + order +
                ", read=" + read +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(order, that.order) &&
                Objects.equals(read, that.read);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, order, read);
    }
}
