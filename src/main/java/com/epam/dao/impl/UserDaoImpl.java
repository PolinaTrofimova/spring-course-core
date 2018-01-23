package com.epam.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import com.epam.dao.UserDao;
import org.springframework.stereotype.Repository;

import com.epam.domain.User;

@Repository
public class UserDaoImpl implements UserDao {

    private Map<Long, User> users = new ConcurrentHashMap<Long, User>();
    private AtomicLong index = new AtomicLong(0);

    public User create(User user) {
        user.setId(index.getAndIncrement());
        users.put(user.getId(), user);
        return user;
    }

    public void remove(Long id) {
        users.remove(id);
    }

    public User findById(Long id) {
        return users.get(id);
    }

    public User findByEmail(String email) {
        return users.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst().orElse(null);
    }

    public List<User> findByName(String name) {
        return  users.values().stream().
                filter(user -> user.getName().equals(name)).
                collect(Collectors.toList());
    }
}
