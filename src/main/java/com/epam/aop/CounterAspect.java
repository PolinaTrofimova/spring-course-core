package com.epam.aop;

import com.epam.domain.Event;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.HashMap;
import java.util.Map;


@Aspect
public class CounterAspect {
	private Map<String, Long> eventsCounter = new HashMap<String,Long>();
	private Map<String, Long> pricesCounter = new HashMap<String,Long>();
	private Map<String, Long> bookingsCounter = new HashMap<String,Long>();
		
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


	
	@Pointcut("execution(* com.epam.service.impl.EventServiceImpl.getByName(..))")
	private void getEventByName(){}

	@AfterReturning(pointcut="getEventByName()", returning="event")
	private void countGettingEvent(Event event){
		if(event != null) {
			count(event.getName(), eventsCounter);
		}
	}
	
	@Pointcut("execution(* com.epam.domain.Event.getPrice())")
	private void getEventPrice(){}

	@AfterReturning(pointcut="getEventPrice()")
	private void countGettingPrice(JoinPoint joinPoint){
		Event event = (Event) joinPoint.getTarget();
		count(event.getName(), pricesCounter);
		
	}
		
	private void count(String name, Map<String,Long> map){
		Long counter = map.get(name);
		if (counter != null) {
			counter += 1;
		} else {
			counter = 1L;
		}
		map.put(name, counter);
	}


}
