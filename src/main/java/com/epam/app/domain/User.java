package com.epam.app.domain;

import java.util.List;
import java.util.Objects;

import static com.epam.app.utility.CollectionUtility.nullSafeListInitialize;

public class User {

    private final Integer id;
    private final String name;
    private final String email;
    private final String password;
    private final List<Appointment> appointments;
    private final Role role;

    private User(Builder builder) {
        id = builder.id;
        name = builder.name;
        email = builder.email;
        password = builder.password;
        appointments = builder.appointments;
        role = builder.role;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public Role getRole() {
        return role;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Integer id;
        private String name;
        private String email;
        private String password;
        private List<Appointment> appointments;
        private Role role;

        private Builder() {
        }

        public Builder setId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setAppointments(List<Appointment> appointments) {
            this.appointments = appointments;
            return this;
        }

        public Builder setRole(Role role) {
            this.role = role;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
