package com.epam.service;

import com.epam.domain.Auditorium;

import java.util.List;

public interface AuditoriumService {
    List<Auditorium> getAll();

    Auditorium getByName(String name);
}
