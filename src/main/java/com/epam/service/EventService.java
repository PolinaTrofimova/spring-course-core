package com.epam.service;

import com.epam.domain.Auditorium;
import com.epam.domain.Event;
import com.epam.domain.Rating;
import com.epam.domain.Show;
import org.joda.time.DateTime;

import java.util.List;

public interface EventService {
    Event create(String name, Long price, Rating rating);

    void remove(Long id);

    Event getByName(String name);

    List<Show> getForDateRange(DateTime from, DateTime to);

    List<Show> getNextEvents(DateTime to);

    Show assignAuditorium(Event event, Auditorium auditorium, DateTime time);
}
