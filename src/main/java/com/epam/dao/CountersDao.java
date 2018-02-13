package com.epam.dao;

import com.epam.domain.Event;
import com.epam.domain.User;

public interface CountersDao {

    void countGetEvent(Event event);
    Long getCounterGetEvent(Event event);

    void countGetPrice(Event event);
    Long getCounterGetPrice(Event event);

    void countBookTicket(Event event);
    Long getCounterBookTicket(Event event);

    Long getCounterTenthDiscounts();
    void countTenthDiscount(User user);
    Long getCounterTenthDiscount(User user);

    Long getCounterBirthdayDiscounts();
    Long getCounterBirthdayDiscount(User user);
    void countBirthdayDiscount(User user);
}
