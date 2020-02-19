package com.salon.booking.domain;

import java.util.Objects;

public class Notification {

    private final Integer id;
    private final String text;
    private final User user;
    private final NotificationType type;

    private Notification(Builder builder) {
        id = builder.id;
        text = builder.text;
        user = builder.user;
        type = builder.type;
    }

    public Integer getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public User getUser() {
        return user;
    }

    public NotificationType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", user=" + user +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(text, that.text) &&
                Objects.equals(user, that.user) &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, user, type);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(Notification copy) {
        Builder builder = new Builder();
        builder.id = copy.getId();
        builder.text = copy.getText();
        builder.user = copy.getUser();
        builder.type = copy.getType();
        return builder;
    }


    public static final class Builder {
        private Integer id;
        private String text;
        private User user;
        private NotificationType type;

        private Builder() {
        }

        public Builder setId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder setType(NotificationType type) {
            this.type = type;
            return this;
        }

        public Notification build() {
            return new Notification(this);
        }
    }
}
