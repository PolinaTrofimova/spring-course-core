package com.epam.aop;

import com.epam.domain.Ticket;
import com.epam.domain.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.joda.time.DateTime;


@Aspect
public class LuckyWinnerAspect {
    private static final double LUCKY_PERCENT = 0.25d;

    @Pointcut("execution(* com.epam.service.impl.BookingServiceImpl.bookTicket(..))")
    private void getBookingTicket() {
    }

    @AfterReturning(pointcut = "getBookingTicket()", returning = "ticket")
    private void tryLucky(JoinPoint joinPoint, Ticket ticket) {
        Object[] args = joinPoint.getArgs();
        User user = null;
        for (Object object : args) {
            if (object instanceof User) {
                user = (User) object;
            }
        }
        if (user == null) {
            return;
        }
        if (checkLucky()) {
            ticket.setPrice(0L);
            user.getComments().add(new DateTime() + ": free ticket");
        }
    }

    private static boolean checkLucky() {
        return Math.random() > (1 - LUCKY_PERCENT);
    }


}
