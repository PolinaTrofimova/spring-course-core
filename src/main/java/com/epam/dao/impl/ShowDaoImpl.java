package com.epam.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.epam.dao.ShowDao;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import com.epam.domain.Auditorium;
import com.epam.domain.Event;
import com.epam.domain.Show;


@Repository
public class ShowDaoImpl implements ShowDao {

	private List<Show> shows = new ArrayList<Show>();

	public Show assign(Event event, Auditorium auditorium, DateTime time) {
		Show entry = new Show();
		entry.setEvent(event);
		entry.setAuditorium(auditorium);
		entry.setTime(time);

		shows.add(entry);

		return entry;
	}

	public List<Show> findForDateRange(DateTime from, DateTime to) {
		List<Show> result = new ArrayList<Show>();
		for (Show entry : shows) {
			DateTime time = entry.getTime();
			if (time.isAfter(from) && time.isBefore(to)) {
				result.add(entry);
			}
		}
		return result;
	}


}
