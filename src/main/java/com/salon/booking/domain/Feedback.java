package com.salon.booking.domain;

import java.util.Objects;

public class Feedback {

    private final Integer id;
    private final String text;
    private final Order order;
    private final FeedbackStatus status;

    private Feedback(Builder builder) {
        id = builder.id;
        text = builder.text;
        order = builder.order;
        status = builder.status;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(Feedback copy) {
        Builder builder = new Builder();
        builder.id = copy.getId();
        builder.text = copy.getText();
        builder.order = copy.getOrder();
        builder.status = copy.getStatus();
        return builder;
    }

    public Integer getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Order getOrder() {
        return order;
    }

    public FeedbackStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", order=" + order +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return Objects.equals(id, feedback.id) &&
                Objects.equals(text, feedback.text) &&
                Objects.equals(order, feedback.order) &&
                status == feedback.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, order, status);
    }


    public static final class Builder {
        private Integer id;
        private String text;
        private Order order;
        private FeedbackStatus status;

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

        public Builder setOrder(Order order) {
            this.order = order;
            return this;
        }

        public Builder setStatus(FeedbackStatus status) {
            this.status = status;
            return this;
        }

        public Feedback build() {
            return new Feedback(this);
        }
    }
}
