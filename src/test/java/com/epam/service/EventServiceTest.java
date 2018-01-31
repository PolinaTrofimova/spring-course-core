package com.epam.service;

import com.epam.domain.Auditorium;
import com.epam.domain.Event;
import com.epam.domain.Rating;
import com.epam.domain.Show;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/root-context.xml")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class EventServiceTest {

    @Autowired
    private
    EventService eventService;
    @Autowired
    private
    UserService userService;
    @Autowired
    private
    AuditoriumService auditoriumService;


    @Before
    public void setUp() {
        DateTime birthday = new DateTime(2000, 12, 31, 12, 0);
        String testName = "Ivanov";
        String testEmail = "ivanov@com";

        userService.register(testName, testEmail, birthday);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void registerNewEvent() {
        String name = "Going Vertical";
        Long price = 300L;
        Rating rating = Rating.HIGH;

        Event event = eventService.create(name, price, rating);

        assertEquals(name, event.getName());
        assertEquals(price, event.getPrice());
        assertEquals(rating, event.getRating());
    }

    @Test
    public void getByName() {
        String name = "Going Vertical";
        Long price = 300L;
        Rating rating = Rating.HIGH;

        Event event = eventService.create(name, price, rating);

        Event eventByName = eventService.getByName(name);

        assertEquals(eventByName.getName(), event.getName());
        assertEquals(eventByName.getPrice(), event.getPrice());
        assertEquals(eventByName.getRating(), event.getRating());
        assertEquals(eventByName.getId(), event.getId());
    }

    @Test
    public void getByWrongName() {
        String name = "Going Vertical";
        Long price = 300L;
        Rating rating = Rating.HIGH;

        eventService.create(name, price, rating);

        Event eventByName = eventService.getByName("wrong_name");

        assertEquals(eventByName, null);
    }

    @Test
    public void removeEvent() {
        String name = "Going Vertical";
        Long price = 300L;
        Rating rating = Rating.HIGH;

        Event event = eventService.create(name, price, rating);

        eventService.remove(event.getId());
        Event eventByName = eventService.getByName(name);

        assertEquals(eventByName, null);
    }

    @Test
    public void getForDateRange() {
        String name = "Going Vertical";
        Long price = 300L;
        Rating rating = Rating.HIGH;

        Event event = eventService.create(name, price, rating);

        Auditorium auditorium = auditoriumService.getByName("Red");
        DateTime time = new DateTime(2017, 4, 3, 12, 0);

        eventService.assignAuditorium(event, auditorium, time);

        DateTime from = new DateTime(2017, 4, 2, 11, 0);
        DateTime to = new DateTime(2017, 4, 4, 13, 0);
        List<Show> list = eventService.getForDateRange(from, to);

        assertEquals(list.size(), 1);
    }


    @Test
    public void getNextEvents() {
        String name = "Going Vertical";
        Long price = 300L;
        Rating rating = Rating.HIGH;

        Event event = eventService.create(name, price, rating);

        Auditorium auditorium = auditoriumService.getByName("Red");
        DateTime time = new DateTime(2017, 4, 3, 12, 0);

        eventService.assignAuditorium(event, auditorium, time);

        DateTime to = new DateTime(2017, 4, 4, 13, 0);
        List<Show> list = eventService.getNextEvents(to);

        assertEquals(list.size(), 0);//!


    }

    @Test
    public void addEventsPrototype() {
        String name = "Going Vertical";
        Long price = 300L;
        Rating rating = Rating.HIGH;

        eventService.create(name, price, rating);

        String name2 = "Going Vertical2";
        Long price2 = 400L;
        Rating rating2 = Rating.MID;

        eventService.create(name2, price2, rating2);

        Event eventByName = eventService.getByName(name);
        Event eventByName2 = eventService.getByName(name2);

        assertEquals(eventByName.getName(), name);
        assertEquals(eventByName.getPrice(), price);
        assertEquals(eventByName.getRating(), rating);

        assertEquals(eventByName2.getName(), name2);
        assertEquals(eventByName2.getPrice(), price2);
        assertEquals(eventByName2.getRating(), rating2);
    }


}



