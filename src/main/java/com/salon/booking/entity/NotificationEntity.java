package com.salon.booking.entity;

public class NotificationEntity {

    private final Integer id;
    private final String text;
    private final UserEntity user;
    private final Boolean read;
    private final NotificationTypeEntity type;

    private NotificationEntity(Builder builder) {
        id = builder.id;
        text = builder.text;
        user = builder.user;
        read = builder.read;
        type = builder.type;
    }

    public Boolean getRead() {
        return read;
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

    public static Builder builder(NotificationEntity copy) {
        Builder builder = new Builder();
        builder.id = copy.getId();
        builder.text = copy.getText();
        builder.user = copy.getUser();
        builder.read = copy.getRead();
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
        private Boolean read;
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

        public Builder setRead(Boolean read) {
            this.read = read;
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
