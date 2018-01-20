package com.epam.dao;

import java.util.List;

import org.joda.time.DateTime;

import com.epam.domain.Auditorium;
import com.epam.domain.Event;
import com.epam.domain.Show;

public interface ShowDao {

    List<Show> findForDateRange(DateTime from, DateTime to);

    Show assign(Show show);

}
