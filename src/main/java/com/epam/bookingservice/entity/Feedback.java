package com.epam.bookingservice.entity;

import java.util.Objects;

public class Feedback {

    private final Integer id;
    private final String text;
    private final FeedbackStatus status;
    private final User worker;

    private Feedback(Builder builder) {
        id = builder.id;
        text = builder.text;
        status = builder.status;
        worker = builder.worker;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(Feedback copy) {
        Builder builder = new Builder();
        builder.id = copy.getId();
        builder.text = copy.getText();
        builder.status = copy.getStatus();
        builder.worker = copy.getWorker();
        return builder;
    }

    public User getWorker() {
        return worker;
    }

    public Integer getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public FeedbackStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", status=" + status +
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
        Feedback feedback = (Feedback) o;
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
        private FeedbackStatus status;
        private User worker;

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

        public Builder setStatus(FeedbackStatus status) {
            this.status = status;
            return this;
        }

        public Builder setWorker(User worker) {
            this.worker = worker;
            return this;
        }

        public Feedback build() {
            return new Feedback(this);
        }
    }
}
