package com.epam.aop.map;

import org.aspectj.lang.annotation.Aspect;

import java.util.HashMap;
import java.util.Map;

public class MetaInfCounter {
    private Map<String, Long> eventsCounter = new HashMap<>();
    private Map<String, Long> pricesCounter = new HashMap<>();
    private Map<String, Long> bookingsCounter = new HashMap<>();

    public Map<String, Long> getEventsCounter() {
        return eventsCounter;
    }

    public Map<String, Long> getPricesCounter() {
        return pricesCounter;
    }

    public Map<String, Long> getBookingsCounter() {
        return bookingsCounter;
    }

    public void setEventsCounter(Map<String, Long> eventsCounter) {
        this.eventsCounter = eventsCounter;
    }

    public void setPricesCounter(Map<String, Long> pricesCounter) {
        this.pricesCounter = pricesCounter;
    }

    public void setBookingsCounter(Map<String, Long> bookingsCounter) {
        this.bookingsCounter = bookingsCounter;
    }

}
