package com.epam.dao.impl.jdbc;

import com.epam.dao.ShowDao;
import com.epam.dao.impl.jdbc.mapper.ShowMapper;
import com.epam.domain.*;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JDBCShowDaoImpl implements ShowDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final String TABLE_NAME = "shows";
	private static final String PK_COLUMN = "id";
	private static final String DELETE_SQL = "DELETE FROM " + TABLE_NAME + " WHERE " + PK_COLUMN + " = ?";
	private static final String SELECT_SQL = "SELECT * FROM " + TABLE_NAME + " WHERE ";

	@Override
	public Show assign(Show show){
		SimpleJdbcInsert insert = new SimpleJdbcInsert(this.jdbcTemplate).withTableName(TABLE_NAME).usingGeneratedKeyColumns(PK_COLUMN);

		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("event", show.getEvent().getId());
		parameters.put("auditorium", show.getAuditorium().getId());
		parameters.put("showtime", new Timestamp(show.getTime().getMillis()));
		final Number key = insert.executeAndReturnKey(parameters);
		show.setId(key.longValue());
		return show;
	}

    @Override
	public List<Show> findForDateRange(DateTime from, DateTime to) {

        Timestamp fromSql = new Timestamp(from.getMillis());
        Timestamp toSql = new Timestamp(to.getMillis());
        String sql = SELECT_SQL + " SHOWTIME BETWEEN ? AND ?";
        return jdbcTemplate.query(sql, new Object[] {fromSql, toSql}, getNewMapper());

	}

    private ShowMapper getNewMapper(){
        return new ShowMapper();
    }



}
