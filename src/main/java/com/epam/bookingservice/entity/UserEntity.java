package com.epam.bookingservice.entity;

import java.util.Objects;

public class UserEntity {

    private final Integer id;
    private final String name;
    private final String email;
    private final String password;
    private final RoleEntity role;
    private final UserStatusEntity status;

    private UserEntity(Builder builder) {
        id = builder.id;
        name = builder.name;
        email = builder.email;
        password = builder.password;
        role = builder.role;
        status = builder.status;
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

    public RoleEntity getRole() {
        return role;
    }

    public UserStatusEntity getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
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
        UserEntity user = (UserEntity) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(name, user.name) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                role == user.role &&
                status == user.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password, role, status);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(UserEntity copy) {
        Builder builder = new Builder();
        builder.id = copy.getId();
        builder.name = copy.getName();
        builder.email = copy.getEmail();
        builder.password = copy.getPassword();
        builder.role = copy.getRole();
        builder.status = copy.getStatus();
        return builder;
    }


    public static final class Builder {
        private Integer id;
        private String name;
        private String email;
        private String password;
        private RoleEntity role;
        private UserStatusEntity status;

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

        public Builder setRole(RoleEntity role) {
            this.role = role;
            return this;
        }

        public Builder setStatus(UserStatusEntity status) {
            this.status = status;
            return this;
        }

        public UserEntity build() {
            return new UserEntity(this);
        }
    }
}
