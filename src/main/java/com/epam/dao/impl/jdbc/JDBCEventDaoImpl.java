package com.epam.dao.impl.jdbc;

import com.epam.dao.EventDao;
import com.epam.domain.Event;
import com.epam.domain.Rating;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class JDBCEventDaoImpl implements EventDao {
    @Inject
    private JdbcTemplate jdbcTemplate;

    private static final String TABLE_NAME = "events";
    private static final String PK_COLUMN = "id";
    private static final String DELETE_SQL = "DELETE FROM " + TABLE_NAME + " WHERE " + PK_COLUMN + " = ?";
    private static final String SELECT_SQL = "SELECT * FROM " + TABLE_NAME + " WHERE ";

    @Override
    public Event create(Event event) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(this.jdbcTemplate).withTableName(TABLE_NAME).usingGeneratedKeyColumns(PK_COLUMN);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", event.getName());
        parameters.put("price", event.getPrice());
        parameters.put("rating", event.getRating());
        final Number key = insert.executeAndReturnKey(parameters);
        event.setId(key.longValue());
        return event;
    }

    @Override
    public void remove(Long id) {
        int rows = jdbcTemplate.update(DELETE_SQL, id);
    }


    @Override
    public Event getByName(String name) {
        return findByParam("name", name);
    }


    private Event findByParam(String param, Object value) {
        String sql = SELECT_SQL + param + " = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{value}, getNewMapper());
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    private EventMapper getNewMapper() {
        return new EventMapper();
    }
//todo:вынеси в отдельный class in packeg mapper
    private static final class EventMapper implements RowMapper<Event> {
        public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
            Event event = new Event();
            event.setId(rs.getLong("id"));
            event.setName(rs.getString("name"));
            event.setPrice(rs.getLong("price"));
            event.setRating(Rating.valueOf(rs.getString("rating")));
            return event;
        }
    }
}
