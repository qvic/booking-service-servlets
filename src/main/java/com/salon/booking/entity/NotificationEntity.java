package com.salon.booking.entity;

import java.util.Objects;

public class NotificationEntity {

    private final Integer id;
    private final OrderEntity order;
    private final Boolean read;

    public NotificationEntity(Integer id, OrderEntity order, Boolean read) {
        this.id = id;
        this.order = order;
        this.read = read;
    }

    public Integer getId() {
        return id;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public Boolean getRead() {
        return read;
    }

    @Override
    public String toString() {
        return "NotificationEntity{" +
                "id=" + id +
                ", order=" + order +
                ", read=" + read +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationEntity that = (NotificationEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(order, that.order) &&
                Objects.equals(read, that.read);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, order, read);
    }
}
