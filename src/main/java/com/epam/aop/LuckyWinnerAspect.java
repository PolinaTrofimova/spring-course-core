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
    private static final Long ZERO = 0L;
    private static final int one = 1;

    @Pointcut("execution(* com.epam.service.impl.BookingServiceImpl.bookTicket(..))")
    private void getBookingTicket() {
    }

    @AfterReturning(pointcut = "getBookingTicket()", returning = "ticket")
    private void tryLucky(JoinPoint joinPoint, Ticket ticket) {
        User user = (User) joinPoint.getArgs()[0];
        if ((user != null) & (checkLucky())) {
            ticket.setPrice(ZERO);
            user.getComments().add(new DateTime() + ": free ticket");
        }
    }

    private static boolean checkLucky() {
        return Math.random() > (one - LUCKY_PERCENT);
    }


}
