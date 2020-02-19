package com.salon.booking.domain;

import java.util.Objects;

public class Feedback {

    private final String text;
    private final User worker;

    public Feedback(String text, User worker) {
        this.text = text;
        this.worker = worker;
    }

    public String getText() {
        return text;
    }

    public User getWorker() {
        return worker;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "text='" + text + '\'' +
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
        Feedback feedback = (Feedback) o;
        return Objects.equals(text, feedback.text) &&
                Objects.equals(worker, feedback.worker);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, worker);
    }
}
