package com.salon.booking.entity;

import java.util.Objects;

public class NotificationEntity {

    private final Integer id;
    private final String text;
    private final UserEntity user;
    private final NotificationTypeEntity type;

    private NotificationEntity(Builder builder) {
        id = builder.id;
        text = builder.text;
        user = builder.user;
        type = builder.type;
    }

    public Integer getId() {
        return id;
    }

    public UserEntity getUser() {
        return user;
    }

    public String getText() {
        return text;
    }

    public NotificationTypeEntity getType() {
        return type;
    }

    @Override
    public String toString() {
        return "NotificationEntity{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", user=" + user +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NotificationEntity that = (NotificationEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(text, that.text) &&
                Objects.equals(user, that.user) &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, user, type);
    }

    public static Builder builder(NotificationEntity copy) {
        Builder builder = new Builder();
        builder.id = copy.getId();
        builder.text = copy.getText();
        builder.user = copy.getUser();
        builder.type = copy.getType();
        return builder;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Integer id;
        private String text;
        private UserEntity user;
        private NotificationTypeEntity type;

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

        public Builder setUser(UserEntity user) {
            this.user = user;
            return this;
        }

        public Builder setType(NotificationTypeEntity type) {
            this.type = type;
            return this;
        }

        public NotificationEntity build() {
            return new NotificationEntity(this);
        }
    }
}