package com.epam.aop;

import com.epam.aop.map.MetaInfDiscount;
import com.epam.domain.User;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.HashMap;
import java.util.Map;


@Aspect
public class DiscountAspect {
    private static final Long ZERO = 0L;
    private static final Long ONE = 1L;

    MetaInfDiscount meta = new MetaInfDiscount();

    private Long ttdCounter = meta.getTenthTicketDiscountCounter();
    private Long bdCounter = meta.getBirthdayDiscountCounter();

    @Pointcut("execution(* com.epam.service.discount.impl.TenthTicketDiscountStrategyImpl.execute(..))")
    private void getTenthTicketDiscount() {
    }

    @AfterReturning(pointcut = "getTenthTicketDiscount() && args(user,..)", returning = "result")
    private void countTenthTicketDiscounts(Long result, User user) {
        if (result > ZERO) {
            count(user.getName(), meta.getTenthTicketDiscounts());
            ttdCounter++;
        }
    }

    @Pointcut("execution(* com.epam.service.discount.impl.BirthdayDiscountStrategyImpl.execute(..))")
    private void getBirthdayDiscount() {
    }

    @AfterReturning(pointcut = "getBirthdayDiscount() && args(user,..)", returning = "result")
    private void countBirthdayDiscounts(Long result, User user) {
        if (result > ZERO) {
            count(user.getName(), meta.getBirthdayDiscounts());
            bdCounter++;
        }
    }

    private void count(String name, Map<String, Long> map) {
        Long counter = map.get(name);
        map.put(name, (counter != null ? (counter+1) : ONE));
    }
}
