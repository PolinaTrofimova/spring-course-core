package com.epam.dao.impl.jdbc;


import com.epam.dao.CountersDao;
import com.epam.domain.Event;
import com.epam.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;

public class JDBCCountersDaoImpl implements CountersDao {
    @Inject
    private JdbcTemplate jdbcTemplate;


    @Override
    public void countGetEvent(Event event) {
        if (event != null && event.getId() != null) {
            jdbcTemplate.update("call count_get_event(?)", event.getId());
        }
    }

    @Override
    public Long getCounterGetEvent(Event event) {
        try {
            return jdbcTemplate.queryForObject("SELECT get_counter from event_counters where event_id=?", new Object[]{event.getId()}, Long.class);
        } catch (EmptyResultDataAccessException e) {
            return 0L;
        }
    }

    @Override
    public void countGetPrice(Event event) {
        if (event != null && event.getId() != null) {
            jdbcTemplate.update("call count_get_price_event(?)", event.getId());
        }
    }

    @Override
    public Long getCounterGetPrice(Event event) {
        try {
            return jdbcTemplate.queryForObject("SELECT price_counter from event_counters where event_id=?", new Object[]{event.getId()}, Long.class);
        } catch (EmptyResultDataAccessException e) {
            return 0L;
        }
    }

    @Override
    public void countBookTicket(Event event) {
        if (event != null && event.getId() != null) {
            jdbcTemplate.update("call count_book_event(?)", event.getId());
        }
    }

    @Override
    public Long getCounterBookTicket(Event event) {
        try {
            return jdbcTemplate.queryForObject("SELECT book_counter from event_counters where event_id=?", new Object[]{event.getId()}, Long.class);
        } catch (EmptyResultDataAccessException e) {
            return 0L;
        }
    }

    @Override
    public void countTenthDiscount(User user) {
        if (user != null && user.getId() != null) {
            jdbcTemplate.update("call count_tenth_discount(?)", user.getId());
        }
    }

    @Override
    public Long getCounterTenthDiscount(User user) {
        try {
            return jdbcTemplate.queryForObject("SELECT tenth_counter from discount_counters where user_id=?", new Object[]{user.getId()}, Long.class);
        } catch (EmptyResultDataAccessException e) {
            return 0L;
        }
    }

    @Override
    public void countBirthdayDiscount(User user) {
        if (user != null && user.getId() != null) {
            jdbcTemplate.update("call count_birthday_discount(?)", user.getId());
        }
    }

    @Override
    public Long getCounterBirthdayDiscount(User user) {
        try {
            return jdbcTemplate.queryForObject("SELECT birthday_counter from discount_counters where user_id=?", new Object[]{user.getId()}, Long.class);
        } catch (EmptyResultDataAccessException e) {
            return 0L;
        }
    }

    @Override
    public Long getCounterBirthdayDiscounts() {
        try {
            return jdbcTemplate.queryForObject("SELECT sum(birthday_counter) from discount_counters", Long.class);
        } catch (EmptyResultDataAccessException e) {
            return 0L;
        }
    }

    @Override
    public Long getCounterTenthDiscounts() {
        try {
            return jdbcTemplate.queryForObject("SELECT sum(tenth_counter) from discount_counters", Long.class);
        } catch (EmptyResultDataAccessException e) {
            return 0L;
        }
    }


}
