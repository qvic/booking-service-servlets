package com.salon.booking.entity;

import java.util.Objects;

public class DurationEntity {

    private final Integer id;
    private final Integer minutes;

    public DurationEntity(Integer id, Integer minutes) {
        this.id = id;
        this.minutes = minutes;
    }

    public Integer getId() {
        return id;
    }

    public Integer getMinutes() {
        return minutes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DurationEntity that = (DurationEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(minutes, that.minutes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, minutes);
    }

    @Override
    public String toString() {
        return "DurationEntity{" +
                "id=" + id +
                ", minutes=" + minutes +
                '}';
    }
}
