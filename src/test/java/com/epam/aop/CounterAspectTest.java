package com.epam.aop;

import com.epam.domain.*;
import com.epam.service.*;
import org.joda.time.DateTime;
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
public class CounterAspectTest {

	@Inject	EventService eventService;
	@Inject UserService userService;

	@Inject CounterAspect counterAspect;

	@Before
	public void setUp() throws Exception {
		DateTime birthday = new DateTime(2000, 12, 31, 12, 0);
		String testName = "Ivanov";
		String testEmail = "ivanov@com";
		
		userService.register(testName , testEmail , birthday);
	}
	
	@Test
	public void countGettingEventByName() {
		String name = "Going Vertical";
		Long price = 300L;
		Rating rating = Rating.HIGH;		
		eventService.create(name, price, rating);
		eventService.getByName(name);
		eventService.getByName(name);
		eventService.getByName(name);
		
		String name2 = "Going Vertical2";
		Long price2 = 300L;
		Rating rating2 = Rating.HIGH;		
		eventService.create(name2, price2, rating2);
		
		eventService.getByName(name2);
		eventService.getByName(name2);
		
		Map<String, Long> counter = counterAspect.getEventsCounter();		
		assertEquals((long) counter.get(name), 3);
		assertEquals((long) counter.get(name2), 2);
		assertEquals(counter.get("wrong_name"), null);
	}
	
	@Test
	public void countGettingPrice() {
		String name = "Going Vertical";
		Long price = 300L;
		Rating rating = Rating.HIGH;		
		Event event = eventService.create(name, price, rating);
		
		String name2 = "Going Vertical2";
		Long price2 = 300L;
		Rating rating2 = Rating.HIGH;		
		Event event2 = eventService.create(name2, price2, rating2);
		
		event.getPrice();
		event.getPrice();
		event.getPrice();
		event.getPrice();
		
		event2.getPrice();
		event2.getPrice();
		event2.getPrice();
		
		Map<String, Long> counter = counterAspect.getPricesCounter();		
		assertEquals((long) counter.get(name), 4);
		assertEquals((long) counter.get(name2), 3);
		assertEquals(counter.get("wrong_name"), null);
	}
	
}
