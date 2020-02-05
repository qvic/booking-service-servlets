package com.epam.bookingservice.domain;

import com.epam.bookingservice.entity.RoleEntity;
import com.epam.bookingservice.entity.UserEntity;
import com.epam.bookingservice.entity.UserStatusEntity;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {

    private final Integer id;
    private final String name;
    private final String email;
    private final String password;

    private User(Builder builder) {
        id = builder.id;
        name = builder.name;
        email = builder.email;
        password = builder.password;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(User copy) {
        Builder builder = new Builder();
        builder.id = copy.getId();
        builder.name = copy.getName();
        builder.email = copy.getEmail();
        builder.password = copy.getPassword();
        return builder;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(name, user.name) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password);
    }

    public UserEntity toEntity() {
        return UserEntity.builder()
                .setId(getId())
                .setName(getName())
                .setEmail(getEmail())
                .setPassword(getPassword())
                .setRole(RoleEntity.CLIENT)
                .setStatus(UserStatusEntity.ACTIVE)
                .build();
    }


    public static User fromEntity(UserEntity entity) {
        return User.builder()
                .setId(entity.getId())
                .setName(entity.getName())
                .setEmail(entity.getEmail())
                .setPassword(entity.getPassword())
                .build();
    }

    public static final class Builder {
        private Integer id;
        private String name;
        private String email;
        private String password;

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

        public User build() {
            return new User(this);
        }
    }
}
