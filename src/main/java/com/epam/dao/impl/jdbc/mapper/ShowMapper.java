package com.epam.dao.impl.jdbc.mapper;

import com.epam.domain.Auditorium;
import com.epam.domain.Event;
import com.epam.domain.Show;
import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class ShowMapper implements RowMapper<Show> {

    public Show mapRow(ResultSet rs, int rowNum) throws SQLException {
        Show show = new Show();
        show.setId(rs.getLong("id"));
        Event event = new Event();
        event.setId(rs.getLong("event"));
        show.setEvent(event);
        Auditorium auditorium = new Auditorium();
        auditorium.setId(rs.getLong("auditorium"));
        show.setAuditorium(auditorium);
        show.setTime(new DateTime(rs.getTimestamp("showtime")));
        return show;
    }
}
