package com.epam.dao.impl.jdbc;

import com.epam.dao.UserDao;
import com.epam.domain.User;
import org.joda.time.DateTime;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCUserDaoImpl implements UserDao {

    @Inject
    private JdbcTemplate jdbcTemplate;

    private static final String TABLE_NAME = "users";
    private static final String PK_COLUMN = "id";
    private static final String DELETE_SQL = "DELETE FROM " + TABLE_NAME + " WHERE " + PK_COLUMN + " = ?";
    private static final String SELECT_SQL = "SELECT * FROM " + TABLE_NAME + " WHERE ";

    @Override
    public User create(User user) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(this.jdbcTemplate).withTableName(TABLE_NAME).usingGeneratedKeyColumns(PK_COLUMN);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", user.getName());
        parameters.put("email", user.getEmail());
        parameters.put("birthday", new Timestamp(user.getBirthday().getMillis()));
        final Number key = insert.executeAndReturnKey(parameters);
        user.setId(key.longValue());
        return user;
    }

    @Override
    public void remove(Long id) {
        int rows = jdbcTemplate.update(DELETE_SQL, id);
    }

    @Override
    public User findById(Long id) {
        return findByParam("id", id);
    }

    @Override
    public List<User> findByName(String name) {
        return findListByParam("name", name);
    }

    @Override
    public User findByEmail(String email) {
        return findByParam("email", email);
    }

    private List<User> findListByParam(String param, Object value) {
        String sql = SELECT_SQL + param + " = ?";
        return jdbcTemplate.query(sql, new Object[]{value}, getNewMapper());
    }

    private User findByParam(String param, Object value) {
        String sql = SELECT_SQL + param + " = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{value}, getNewMapper());
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    private UserMapper getNewMapper() {
        return new UserMapper();
    }

    private static final class UserMapper implements RowMapper<User> {

        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setBirthday(new DateTime(rs.getTimestamp("birthday")));
            return user;
        }
    }


}
