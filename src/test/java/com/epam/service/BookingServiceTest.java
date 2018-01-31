package com.epam.service;

import com.epam.domain.*;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/root-context.xml")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class BookingServiceTest {

	@Inject
	BookingService bookingService;
	@Inject
	AuditoriumService auditoriumService;
	@Inject
	EventService eventService;
	@Inject
	UserService userService;

	@Test
	public void bookTicket() {
		
		DateTime birthday = new DateTime(2000, 12, 31, 12, 00);
		String testName = "Ivanov";
		String testEmail = "ivanov@com";

		User user = userService.register(testName, testEmail, birthday);

		String eventName = "Going Vertical";
		Long price = 300L;
		Rating rating = Rating.HIGH;

		Event event = eventService.create(eventName, price, rating);

		Auditorium auditorium = auditoriumService.getByName("Red");
		DateTime time = new DateTime(2017, 12, 31, 12, 0);

		Show show = eventService.assignAuditorium(event, auditorium, time);

		Long seat = 12L;
		Ticket ticket = bookingService.bookTicket(user, show, seat);
		
		assertEquals(ticket.getSeat(), seat);
		assertEquals(ticket.getUser().getName(), testName);
		assertEquals(ticket.getShow().getEvent().getName(), eventName);
	}
	
	@Test
	public void bookAnonymousTicket() {
		DateTime birthday = new DateTime(2000, 12, 31, 12, 00);
		String testName = "Ivanov";
		String testEmail = "ivanov@com";

		User user = userService.register(testName, testEmail, birthday);

		String eventName = "Going Vertical";
		Long price = 300L;
		Rating rating = Rating.HIGH;

		Event event = eventService.create(eventName, price, rating);

		Auditorium auditorium = auditoriumService.getByName("Red");
		DateTime time = new DateTime(2017, 12, 31, 12, 0);

		Show show = eventService.assignAuditorium(event, auditorium, time);

		Long seat = 13L;
		Ticket ticket = bookingService.bookTicket(user, show, seat);
		
		assertEquals(ticket.getSeat(), seat);
		assertEquals(ticket.getUser().getName(), testName);
		assertEquals(ticket.getShow().getEvent().getName(), eventName);
	}
	
	@Test
	public void getTicketsForShow() {
		DateTime birthday = new DateTime(2000, 12, 31, 12, 00);
		String testName = "Ivanov";
		String testEmail = "ivanov@com";
		
		User user = userService.register(testName , testEmail , birthday);
				
		String eventName = "Going Vertical";
		Long price = 300L;
		Rating rating = Rating.HIGH;

		Event event = eventService.create(eventName, price, rating);

		Auditorium auditorium = auditoriumService.getByName("Red");
		DateTime time = new DateTime(2017, 12, 31, 12, 0);

		Show show = eventService.assignAuditorium(event, auditorium, time);

		Long seat = 13L;
		bookingService.bookTicket(user, show, seat);
		
		Map<Long, Ticket> tickets = bookingService.getTicketsForShow(show);
		
		assertEquals(tickets.size(), 1);
		assertNotEquals(tickets.get(13L), null);
	}
	
	@Test
	public void getAnonymousTicketsForShow() {
				
		String eventName = "Going Vertical";
		Long price = 300L;
		Rating rating = Rating.HIGH;

		Event event = eventService.create(eventName, price, rating);

		Auditorium auditorium = auditoriumService.getByName("Red");
		DateTime time = new DateTime(2017, 12, 31, 12, 0);

		Show show = eventService.assignAuditorium(event, auditorium, time);

		Long seat = 13L;
		bookingService.bookTicket(null, show, seat);
		
		Map<Long, Ticket> tickets = bookingService.getTicketsForShow(show);
		
		assertEquals(tickets.size(), 1);
		assertNotEquals(tickets.get(13L), null);
		assertEquals(tickets.get(13L).getUser(), null);
	}
	
	@Test
	public void getTicketPrice() {
		DateTime birthday = new DateTime(2000, 12, 31, 12, 00);
		String testName = "Ivanov";
		String testEmail = "ivanov@com";
		
		User user = userService.register(testName , testEmail , birthday);
				
		String eventName = "Going Vertical";
		Long price = 300L;
		Rating rating = Rating.HIGH;

		Event event = eventService.create(eventName, price, rating);

		Auditorium auditorium = auditoriumService.getByName("Red");
		DateTime time = new DateTime(2017, 12, 31, 12, 0);

		Show show = eventService.assignAuditorium(event, auditorium, time);

		List<Long> seats = new ArrayList<Long>();
		seats.add(11L);
		Long userPrice = bookingService.getTicketPrice(show, seats, user);
		
		
		assertEquals((long) userPrice, 285L);
	}

}



