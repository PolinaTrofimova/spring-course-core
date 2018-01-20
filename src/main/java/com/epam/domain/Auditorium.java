package com.epam.domain;

import java.io.Serializable;
import java.util.List;

public class Auditorium implements Serializable {

    private static final long serialVersionUID = 6532954888032413782L;

    private Long id;
    private String name;
    private int numSeats;
    private List<Long> vipSeats;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumSeats() {
        return numSeats;
    }

    public void setNumSeats(int numSeats) {
        this.numSeats = numSeats;
    }

    public List<Long> getVipSeats() {
        return vipSeats;
    }

    public void setVipSeats(List<Long> vipSeats) {
        this.vipSeats = vipSeats;
    }
}
