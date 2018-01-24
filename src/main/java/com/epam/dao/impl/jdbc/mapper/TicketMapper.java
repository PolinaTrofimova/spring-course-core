package com.epam.dao.impl.jdbc.mapper;

import com.epam.domain.Show;
import com.epam.domain.Ticket;
import com.epam.domain.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class TicketMapper implements RowMapper<Ticket> {

    public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {
        Ticket ticket = new Ticket();
        ticket.setId(rs.getLong("id"));
        Show show = new Show();
        show.setId(rs.getLong("show"));
        ticket.setShow(show);
        ticket.setSeat(rs.getLong("seat"));
        Long userId = rs.getLong("user_id");
        if (userId != -1L) {
            User user = new User();
            user.setId(rs.getLong("user_id"));
            ticket.setUser(user);
        }
        ticket.setMessage(rs.getString("message"));
        return ticket;
    }
}
