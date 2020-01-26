package com.epam.app.repository.impl;

import com.epam.app.domain.User;
import com.epam.app.repository.Page;
import com.epam.app.repository.PageProperties;
import com.epam.app.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserRepositoryImpl implements UserRepository {

    private final Map<Integer, User> userIdToUser = new HashMap<>();

    @Override
    public Optional<User> findByEmail(String email) {
        return userIdToUser.values()
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public User save(User entity) {
        userIdToUser.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<User> findById(Integer id) {
        return Optional.ofNullable(userIdToUser.get(id));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userIdToUser.values());
    }

    @Override
    public Page<User> findAll(PageProperties properties) {
        List<User> items = userIdToUser.values()
                .stream()
                .skip(properties.getLowerBound())
                .limit(properties.getItemsPerPage())
                .collect(Collectors.toList());

        return new Page<>(items, properties, count());
    }

    @Override
    public User update(User entity) {
        if (!userIdToUser.containsKey(entity.getId())) {
            throw new NoSuchElementException();
        }

        userIdToUser.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public void deleteById(Integer id) {
        userIdToUser.remove(id);
    }

    @Override
    public long count() {
        return userIdToUser.size();
    }
}
