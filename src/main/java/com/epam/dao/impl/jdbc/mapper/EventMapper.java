package com.epam.dao.impl.jdbc.mapper;

import com.epam.domain.Event;
import com.epam.domain.Rating;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class EventMapper implements RowMapper<Event> {
    public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
        Event event = new Event();
        event.setId(rs.getLong("id"));
        event.setName(rs.getString("name"));
        event.setPrice(rs.getLong("price"));
        event.setRating(Rating.valueOf(rs.getString("rating")));
        return event;
    }
}