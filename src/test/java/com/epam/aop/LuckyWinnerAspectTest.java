package com.epam.aop;

import com.epam.domain.*;
import com.epam.service.*;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.when;


@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/root-context.xml")
@PrepareForTest({ LuckyWinnerAspect.class })
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class LuckyWinnerAspectTest {

	@Inject EventService eventService;
	@Inject UserService userService;
	@Inject AuditoriumService auditoriumService;
	@Inject BookingService bookingService;

	@Test
	public void getFreeTicket() {
		DateTime birthday = new DateTime(2000, 12, 31, 12, 0);
		String testName = "Ivanov";
		String testEmail = "ivanov@com";

		User user = userService.register(testName, testEmail, birthday);

		String name = "Going Vertical";
		Long price = 300L;
		Rating rating = Rating.HIGH;

		Event event = eventService.create(name, price, rating);

		Auditorium auditorium = auditoriumService.getByName("Red");
		DateTime time = new DateTime(2017, 1, 31, 12, 0);

		Show show = eventService.assignAuditorium(event, auditorium, time);

		PowerMockito.mockStatic(Math.class);
		when(Math.random()).thenReturn(0.99);
        Ticket ticket = bookingService.bookTicket(user, show, 34L);

        assertEquals((long) ticket.getPrice(), 0L);
        assertEquals((long) user.getTickets().get(0).getPrice(), 0L);
        assertEquals(user.getComments().size(), 1L);

	}

    @Test
    public void notGetFreeTicket() {
        DateTime birthday = new DateTime(2000, 12, 31, 12, 0);
        String testName = "Ivanov";
        String testEmail = "ivanov@com";

        User user = userService.register(testName, testEmail, birthday);

        String name = "Going Vertical";
        Long price = 300L;
        Rating rating = Rating.HIGH;

        Event event = eventService.create(name, price, rating);

        Auditorium auditorium = auditoriumService.getByName("Red");
        DateTime time = new DateTime(2017, 1, 31, 12, 0);

        Show show = eventService.assignAuditorium(event, auditorium, time);

        PowerMockito.mockStatic(Math.class);
        when(Math.random()).thenReturn(0.01);
        Ticket ticket = bookingService.bookTicket(user, show, 34L);

        assertNotEquals((long) ticket.getPrice(), 0L);
        assertNotNull(user.getTickets().get(0).getPrice());
        assertNotEquals((long) user.getTickets().get(0).getPrice(), 0L);
        assertEquals(user.getComments().size(), 0L);

    }

}
