package com.epam.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import com.epam.dao.EventDao;
import org.springframework.stereotype.Repository;

import com.epam.domain.Event;

@Repository
public class EventDaoImpl implements EventDao {

    private Map<Long, Event> events = new ConcurrentHashMap<Long, Event>();
    private AtomicLong index = new AtomicLong(0);

    public Event create(Event event) {
        event.setId(index.getAndIncrement());
        events.put(event.getId(), event);
        return event;
    }

    public void remove(Long id) {
        events.remove(id);
    }

    public Event getByName(String name) {
        List<Event> eventsByName = events.values().stream().
                filter(event -> event.getName().equals(name)).
                collect(Collectors.toList());
        return eventsByName.isEmpty() ? null : eventsByName.get(0);
    }
}
