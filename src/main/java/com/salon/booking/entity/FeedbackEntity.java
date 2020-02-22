package com.salon.booking.entity;

import java.util.Objects;

public class FeedbackEntity {

    private final Integer id;
    private final String text;
    private final FeedbackStatusEntity status;
    private final UserEntity worker;

    private FeedbackEntity(Builder builder) {
        id = builder.id;
        text = builder.text;
        status = builder.status;
        worker = builder.worker;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(FeedbackEntity copy) {
        Builder builder = new Builder();
        builder.id = copy.getId();
        builder.text = copy.getText();
        builder.status = copy.getStatus();
        builder.worker = copy.getWorker();
        return builder;
    }

    public UserEntity getWorker() {
        return worker;
    }

    public Integer getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public FeedbackStatusEntity getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "FeedbackEntity{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", status=" + status +
                ", worker=" + worker +
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
        FeedbackEntity feedback = (FeedbackEntity) o;
        return Objects.equals(id, feedback.id) &&
                Objects.equals(text, feedback.text) &&
                status == feedback.status &&
                Objects.equals(worker, feedback.worker);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, status, worker);
    }

    public static final class Builder {
        private Integer id;
        private String text;
        private FeedbackStatusEntity status;
        private UserEntity worker;

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

        public Builder setStatus(FeedbackStatusEntity status) {
            this.status = status;
            return this;
        }

        public Builder setWorker(UserEntity worker) {
            this.worker = worker;
            return this;
        }

        public FeedbackEntity build() {
            return new FeedbackEntity(this);
        }
    }
}
