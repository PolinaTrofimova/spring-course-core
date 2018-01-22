package com.epam.service.impl;

import java.util.List;

import com.epam.domain.Auditorium;
import com.epam.service.AuditoriumService;
import org.springframework.stereotype.Service;

@Service
public class AuditoriumServiceImpl implements AuditoriumService {
    private List<Auditorium> auditoriums;

    public AuditoriumServiceImpl(List<Auditorium> auditoriums) {
        this.auditoriums = auditoriums;
    }

    public List<Auditorium> getAll() {
        return this.auditoriums;
    }

    public Auditorium getByName(String name) {
        //todo use find first
        for (Auditorium auditorium : auditoriums) {
            if (name.equals(auditorium.getName())) {
                return auditorium;
            }
        }
        return null;
    }


}
