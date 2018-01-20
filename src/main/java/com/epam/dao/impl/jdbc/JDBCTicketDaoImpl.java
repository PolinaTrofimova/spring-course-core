package com.epam.dao.impl.jdbc;


import com.epam.dao.TicketDao;
import com.epam.domain.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCTicketDaoImpl implements TicketDao {

    @Inject
    private JdbcTemplate jdbcTemplate;

    private static final String TABLE_NAME = "tickets";
    private static final String PK_COLUMN = "id";
    private static final String DELETE_SQL = "DELETE FROM " + TABLE_NAME + " WHERE " + PK_COLUMN + " = ?";
    private static final String SELECT_SQL = "SELECT * FROM " + TABLE_NAME + " WHERE ";

    @Override
    public Ticket bookTicket(Ticket ticket) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(this.jdbcTemplate).withTableName(TABLE_NAME).usingGeneratedKeyColumns(PK_COLUMN);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("seat", ticket.getSeat());
        parameters.put("show", ticket.getShow().getId());
        if (ticket.getUser() != null && ticket.getUser().getId() != null) {
            parameters.put("user_id", ticket.getUser().getId());
        } else {
            parameters.put("user_id", -1L); //anonymous
        }
        parameters.put("price", ticket.getPrice());
        parameters.put("message", ticket.getMessage() != null ? ticket.getMessage() : "");

        final Number key = insert.executeAndReturnKey(parameters);
        ticket.setId(key.longValue());
        return ticket;
    }

    @Override
    public List<Ticket> findTicketsForShow(Show show) {
        return findListByParam("show", show.getId());
    }

    @Override
    public List<Ticket> findTicketsForUser(User user) {
        return findListByParam("user_id", user.getId());
    }


    private List<Ticket> findListByParam(String param, Object value) {
        String sql = SELECT_SQL + param + " = ?";
        return jdbcTemplate.query(sql, new Object[]{value}, getNewMapper());
    }


    private TicketMapper getNewMapper() {
        return new TicketMapper();
    }

    private static final class TicketMapper implements RowMapper<Ticket> {

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
}
