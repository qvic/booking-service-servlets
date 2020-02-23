package com.salon.booking.entity;

import java.util.Objects;

public class FeedbackEntity {

    private final Integer id;
    private final String text;
    private final FeedbackStatusEntity status;
    private final OrderEntity order;

    private FeedbackEntity(Builder builder) {
        id = builder.id;
        text = builder.text;
        status = builder.status;
        order = builder.order;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(FeedbackEntity copy) {
        Builder builder = new Builder();
        builder.id = copy.getId();
        builder.text = copy.getText();
        builder.status = copy.getStatus();
        builder.order = copy.getOrder();
        return builder;
    }

    public OrderEntity getOrder() {
        return order;
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
                ", order=" + order +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeedbackEntity that = (FeedbackEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(text, that.text) &&
                status == that.status &&
                Objects.equals(order, that.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, status, order);
    }

    public static final class Builder {
        private Integer id;
        private String text;
        private FeedbackStatusEntity status;
        private OrderEntity order;

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

        public Builder setOrder(OrderEntity order) {
            this.order = order;
            return this;
        }

        public FeedbackEntity build() {
            return new FeedbackEntity(this);
        }
    }
}
