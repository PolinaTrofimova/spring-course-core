package com.epam.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.epam.domain.Show;
import com.epam.domain.Ticket;
import com.epam.domain.User;
import com.epam.service.BookingService;
import org.springframework.stereotype.Service;

@Service
public class BookingServiceImpl implements BookingService{
	
	@Inject
	DiscountServiceImpl discountService;
	public Long getTicketPrice(Show show, List<Long> seats, User user){
		Long regularPrice = show.getEvent().getPrice();
		List<Long> vipSeats = show.getAuditorium().getVipSeats();
		Long fullPrice = 0L;
		for(Long seat : seats) {
			if (vipSeats.contains(seat)){
				fullPrice += regularPrice * 2;
			} else {
				fullPrice += regularPrice;
			}
		}
		Long discount = discountService.getDiscount(user, show);
		return (regularPrice * (100 - discount)) / 100;
	}
	
	public Ticket bookTicket(User user, Show show, Long seat) {
		Ticket ticket = new Ticket();
		ticket.setSeat(seat);
		ticket.setShow(show);
		show.getTickets().add(ticket);
		
		if (user != null) {
			ticket.setUser(user);
			user.getTickets().add(ticket);				
		}
		
		return ticket;
	}
	
	public Map<Long, Ticket> getTicketsForShow(Show show) {
		List<Ticket> tickets = show.getTickets();
		
		Map<Long, Ticket> result = new HashMap<Long, Ticket>();		
		for (Ticket ticket : tickets) {
			result.put(ticket.getSeat(), ticket);
		}		
		return result;
	}

}
