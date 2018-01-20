package com.epam.dao;

import com.epam.domain.Show;
import com.epam.domain.Ticket;
import com.epam.domain.User;

import java.util.List;

public interface TicketDao {
    Ticket bookTicket(Ticket ticket);
    List<Ticket> findTicketsForShow(Show show);
    List<Ticket> findTicketsForUser(User user);
}
