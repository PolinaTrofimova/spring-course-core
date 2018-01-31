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

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/root-context.xml")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class DiscountServiceTest {

	@Inject
	DiscountService discountService;
	@Inject
	EventService eventService;
	@Inject
	UserService userService;
	@Inject
	AuditoriumService auditoriumService;


	@Test
	public void getBirthdayDiscount() {
		DateTime birthday = new DateTime(2000, 12, 31, 12, 00);
		String testName = "Ivanov";
		String testEmail = "ivanov@com";

		User user = userService.register(testName, testEmail, birthday);

		String name = "Going Vertical";
		Long price = 300L;
		Rating rating = Rating.HIGH;

		Event event = eventService.create(name, price, rating);

		Auditorium auditorium = auditoriumService.getByName("Red");
		DateTime time = new DateTime(2017, 12, 31, 12, 0);

		Show show = eventService.assignAuditorium(event, auditorium, time);

		Long discount = discountService.getDiscount(user, show);
		assertEquals((long) discount, 5L);
	}
	
	@Test
	public void getTenthTicketDiscount() {
		DateTime birthday = new DateTime(2000, 12, 31, 12, 00);
		String testName = "Ivanov";
		String testEmail = "ivanov@com";

		User user = userService.register(testName, testEmail, birthday);
		for(int i = 0; i<10; i++){
			user.getTickets().add(new Ticket());
		}

		String name = "Going Vertical";
		Long price = 300L;
		Rating rating = Rating.HIGH;

		Event event = eventService.create(name, price, rating);

		Auditorium auditorium = auditoriumService.getByName("Red");
		DateTime time = new DateTime(2017, 01, 31, 12, 0);

		Show show = eventService.assignAuditorium(event, auditorium, time);

		Long discount = discountService.getDiscount(user, show);
		assertEquals((long) discount, 50L);
	}
	
	@Test
	public void getNoDiscount() {
		DateTime birthday = new DateTime(2000, 12, 31, 12, 00);
		String testName = "Ivanov";
		String testEmail = "ivanov@com";

		User user = userService.register(testName, testEmail, birthday);

		String name = "Going Vertical";
		Long price = 300L;
		Rating rating = Rating.HIGH;

		Event event = eventService.create(name, price, rating);

		Auditorium auditorium = auditoriumService.getByName("Red");
		DateTime time = new DateTime(2017, 10, 03, 12, 0);

		Show show = eventService.assignAuditorium(event, auditorium, time);

		Long discount = discountService.getDiscount(user, show);
		assertEquals((long) discount, 0L);
	}
}
