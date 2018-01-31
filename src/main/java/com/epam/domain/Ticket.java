package com.epam.domain;

import java.io.Serializable;

public class Ticket implements Serializable {

    private static final long serialVersionUID = -5090524807183389714L;

    private User user;
    private Long seat;
    private Show show;
    private Long price;

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public Long getSeat() {
        return seat;
    }

    public Show getShow() {
        return show;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setSeat(Long seat) {
        this.seat = seat;
    }

    public void setShow(Show show) {
        this.show = show;
    }


}
