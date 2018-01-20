package com.epam.service;

import com.epam.domain.*;
import com.epam.service.impl.AuditoriumServiceImpl;
import com.epam.service.impl.BookingServiceImpl;
import com.epam.service.impl.EventServiceImpl;
import com.epam.service.impl.UserServiceImpl;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/root-context.xml")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class UserServiceTest {
	
	@Inject
    UserServiceImpl userService;
	@Inject
    BookingServiceImpl bookingService;
	@Inject
    AuditoriumServiceImpl auditoriumService;
	@Inject
    EventServiceImpl eventService;


	@Test
	public void register() {
		DateTime birthday = new DateTime(1990, 12, 31, 12, 00);
		String testName = "Ivanov";
		String testEmail = "ivanov@com";
		
		User user = userService.register(testName , testEmail , birthday);
		assertEquals(testName, user.getName());
		assertEquals(testEmail, user.getEmail());
	}
	
	@Test
	public void getById() {
		DateTime birthday = new DateTime(2000, 12, 31, 12, 00);
		String testName = "Ivanov";
		String testEmail = "ivanov@com";
		
		User user = userService.register(testName , testEmail , birthday);
		
		User userById = userService.getById(user.getId());
		
		assertEquals(user.getBirthday(), userById.getBirthday());
		assertEquals(user.getEmail(), userById.getEmail());
		assertEquals(user.getName(), userById.getName());
				
	}
	
	@Test
	public void getUserByEmail() {
		DateTime birthday = new DateTime(2000, 12, 31, 12, 00);
		String testName = "Ivanov";
		String testEmail = "ivanov@com";
		
		User user = userService.register(testName , testEmail , birthday);
		
		User userById = userService.getUserByEmail(user.getEmail());
		
		assertEquals(user.getBirthday(), userById.getBirthday());
		assertEquals(user.getEmail(), userById.getEmail());
		assertEquals(user.getName(), userById.getName());
				
	}
	
	@Test
	public void getUserByWrongEmail() {
		DateTime birthday = new DateTime(2000, 12, 31, 12, 00);
		String testName = "Ivanov";
		String testEmail = "ivanov@com";
		
		userService.register(testName , testEmail , birthday);
		
		User user = userService.getUserByEmail("wrong@email");
		
		assertEquals(user, null);				
	}
	
	@Test
	public void getUsersByName() {
		DateTime birthday = new DateTime(2000, 12, 31, 12, 00);
		String testName = "Ivanov";
		String testEmail = "ivanov@com";
		
		userService.register(testName , testEmail , birthday);
		
		List<User> users = userService.getUsersByName(testName);
		
		assertEquals(testName, users.get(0).getName());				
	}
	
	
	@Test
	public void remove() {
		DateTime birthday = new DateTime(2000, 12, 31, 12, 00);
		String testName = "Ivanov";
		String testEmail = "ivanov@com";
		
		User user = userService.register(testName , testEmail , birthday);

		userService.remove(user.getId());
		
		Assert.assertEquals(userService.getById(user.getId()), null);
	}

	@Test
	public void getBookedTickets() {
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
		
		Map<Long, Ticket> tickets = userService.getBookedTickets(user.getId());
		
		assertEquals(tickets.size(), 1);
		assertNotEquals(tickets.get(13L), null);
	}
}
