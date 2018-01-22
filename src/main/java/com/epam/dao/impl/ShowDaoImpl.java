package com.epam.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.epam.dao.ShowDao;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import com.epam.domain.Auditorium;
import com.epam.domain.Event;
import com.epam.domain.Show;


@Repository
public class ShowDaoImpl implements ShowDao {

	private List<Show> shows = new ArrayList<>();

	public Show assign(Show show) {
		shows.add(show);
		return show;
	}

	public List<Show> findForDateRange(DateTime from, DateTime to) {
		//todo:example
		return shows.stream().filter(entry -> entry.getTime().isAfter(from) && entry.getTime().isBefore(to))
				.collect(Collectors.toList());
	}
}
