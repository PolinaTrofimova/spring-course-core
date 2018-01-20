package com.epam.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import com.epam.dao.EventDao;
import com.epam.dao.ShowDao;
import com.epam.domain.Auditorium;
import com.epam.domain.Event;
import com.epam.domain.Rating;
import com.epam.domain.Show;
import com.epam.service.EventService;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService {

    @Inject
    EventDao eventDao;
    @Inject
    ShowDao showDao;
    @Inject
    Provider<Event> eventProvider;

    public Event create(String name, Long price, Rating rating) {
        Event event = eventProvider.get();

        event.setName(name);
        event.setPrice(price);
        event.setRating(rating);

        return eventDao.create(event);
    }

    public void remove(Long id) {
        eventDao.remove(id);
    }

    public Event getByName(String name) {
        return eventDao.getByName(name);
    }

    public List<Show> getForDateRange(DateTime from, DateTime to) {
        return showDao.findForDateRange(from, to);
    }

    public List<Show> getNextEvents(DateTime to) {
        return showDao.findForDateRange(new DateTime(), to);
    }

    public Show assignAuditorium(Event event, Auditorium auditorium, DateTime time) {
        Show show = new Show();
        show.setEvent(event);
        show.setAuditorium(auditorium);
        show.setTime(time);
        return showDao.assign(show);
    }

}
