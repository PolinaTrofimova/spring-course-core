package com.epam.aop;

import com.epam.domain.*;
import com.epam.service.*;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/root-context.xml")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class DiscountAspectTest {

	
	@Inject DiscountAspect discountAspect;

	@Inject	DiscountService discountService;
	@Inject	EventService eventService;
	@Inject	UserService userService;
	@Inject	AuditoriumService auditoriumService;
	@Inject	BookingService bookingService;

	@Test
	public void selectBirthdayDiscount() {
		DateTime birthday = new DateTime(2000, 12, 31, 12, 0);
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


		Map<String, Long> birthdays = discountAspect.getBirthdayDiscounts();
		assertEquals((long) birthdays.get(testName), 1);
		assertEquals(birthdays.get("wrong_name"), null);
		assertEquals(discountAspect.getBirthdayDiscountCounter(), 1);

		Map<String, Long> tenths = discountAspect.getTenthTicketDiscounts();
		assertEquals(tenths.get(testName), null);
		assertEquals(tenths.get("wrong_name"), null);
		assertEquals(discountAspect.getTenthTicketDiscountCounter(), 0);
	}

	@Test
	public void selectTenthTicketDiscount() {
		DateTime birthday = new DateTime(2000, 12, 31, 12, 0);
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
		DateTime time = new DateTime(2017, 1, 31, 12, 0);

		Show show = eventService.assignAuditorium(event, auditorium, time);

		for (long i = 10; i < 30; i++) {
			bookingService.bookTicket(user, show, i);
		}

		Map<String, Long> birthdays = discountAspect.getBirthdayDiscounts();
		assertEquals(birthdays.get(testName), null);
		assertEquals(birthdays.get("wrong_name"), null);
		assertEquals(discountAspect.getBirthdayDiscountCounter(), 0);

		Map<String, Long> tenths = discountAspect.getTenthTicketDiscounts();
		assertEquals((long) tenths.get(testName), 2);

		assertEquals(tenths.get("wrong_name"), null);
		assertEquals(discountAspect.getTenthTicketDiscountCounter(), 2);
	}
	
}
