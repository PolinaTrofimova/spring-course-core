package com.epam.aop;

import com.epam.aop.map.MetaInfCounter;
import com.epam.domain.Event;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Map;


@Aspect
public class CounterAspect {
    private static final Long ONE = 1L;

    MetaInfCounter meta = new MetaInfCounter();

    @Pointcut("execution(* com.epam.service.impl.EventServiceImpl.getByName(..))")
    private void getEventByName() {
    }

    @AfterReturning(pointcut = "getEventByName()", returning = "event")
    private void countGettingEvent(Event event) {
        if (event != null) {
            count(event.getName(), meta.getEventsCounter());
        }
    }

    @Pointcut("execution(* com.epam.domain.Event.getPrice())")
    private void getEventPrice() {
    }

    @AfterReturning(pointcut = "getEventPrice()")
    private void countGettingPrice(JoinPoint joinPoint) {
        Event event = (Event) joinPoint.getTarget();
        count(event.getName(), meta.getPricesCounter());

    }

    private void count(String name, Map<String, Long> map) {
        Long counter = map.get(name);
        map.put(name, (counter != null ? (counter+1) : ONE));
    }


}
