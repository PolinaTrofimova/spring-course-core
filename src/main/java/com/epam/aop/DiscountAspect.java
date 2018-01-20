package com.epam.aop;

import com.epam.domain.User;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.HashMap;
import java.util.Map;


@Aspect
public class DiscountAspect {
	private Map<String, Long> tenthTicketDiscounts = new HashMap<String,Long>();
	private Map<String, Long> birthdayDiscounts = new HashMap<String,Long>();
	private long tenthTicketDiscountCounter = 0L;
	private long birthdayDiscountCounter = 0L;
		
	public Map<String, Long> getTenthTicketDiscounts() {
		return tenthTicketDiscounts;
	}
	public Map<String, Long> getBirthdayDiscounts() {
		return birthdayDiscounts;
	}
	public long getTenthTicketDiscountCounter() { return tenthTicketDiscountCounter; }
	public long getBirthdayDiscountCounter() { return birthdayDiscountCounter; }


	@Pointcut("execution(* com.epam.service.discount.impl.TenthTicketDiscountStrategyImpl.execute(..))")
	private void getTenthTicketDiscount(){}

	@AfterReturning(pointcut="getTenthTicketDiscount() && args(user,..)", returning="result")
	private void countTenthTicketDiscounts(Long result, User user){
		if (result > 0L) {
		    count(user.getName(), tenthTicketDiscounts);
			tenthTicketDiscountCounter++;
		}
	}

	@Pointcut("execution(* com.epam.service.discount.impl.BirthdayDiscountStrategyImpl.execute(..))")
	private void getBirthdayDiscount(){}

	@AfterReturning(pointcut="getBirthdayDiscount() && args(user,..)", returning="result")
	private void countBirthdayDiscounts(Long result, User user) {
		if (result > 0L) {
			count(user.getName(), birthdayDiscounts);
			birthdayDiscountCounter++;
		}
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
