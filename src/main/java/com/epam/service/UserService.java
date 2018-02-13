package com.epam.service;

import com.epam.domain.Ticket;
import com.epam.domain.User;
import org.joda.time.DateTime;

import java.util.List;
import java.util.Map;

public interface UserService {
    User register(String name, String email, DateTime birthday);

    void remove(Long id);

    User getById(Long id);

    User getUserByEmail(String email);

    List<User> getUsersByName(String name);

    Map<Long, Ticket> getBookedTickets(Long id);
}
