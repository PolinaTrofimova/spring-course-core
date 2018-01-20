package com.epam.dao;

import com.epam.domain.Event;

public interface EventDao {

	Event create(Event event);

	void remove(Long id);

	Event getByName(String name);

}
