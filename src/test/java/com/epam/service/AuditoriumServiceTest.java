package com.epam.service;

import com.epam.domain.Auditorium;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class AuditoriumServiceTest {

    @Inject
    AuditoriumService auditoriumService;

    @Test
    public void getAuditoriums() {
        List<Auditorium> au = auditoriumService.getAll();
        assertEquals(au.size(), 3);
    }

    @Test
    public void getAuditoriumByName() {
        String name = "Red";
        Auditorium auditorium = auditoriumService.getByName(name);

        assertEquals(auditorium.getName(), name);
        assertEquals(auditorium.getNumSeats(), 50);
    }

    @Test
    public void getAuditoriumByWrongName() {
        String name = "Yellow";
        Auditorium auditorium = auditoriumService.getByName(name);

        assertEquals(auditorium, null);
    }


}



