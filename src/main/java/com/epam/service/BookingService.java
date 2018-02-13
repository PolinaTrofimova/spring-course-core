package com.epam.service;

import com.epam.domain.Show;
import com.epam.domain.Ticket;
import com.epam.domain.User;

import java.util.List;
import java.util.Map;

public interface BookingService {
    Long getTicketPrice(Show show, List<Long> seats, User user);

    Ticket bookTicket(User user, Show show, Long seat);

    Map<Long, Ticket> getTicketsForShow(Show show);
}
