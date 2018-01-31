package com.epam.service.discount.impl;

import com.epam.domain.Show;
import com.epam.domain.User;
import com.epam.service.discount.DiscountStrategy;

public class BirthdayDiscountStrategyImpl implements DiscountStrategy {

	public Long execute(User user, Show entry) {
		 int showDay = entry.getTime().getDayOfMonth();
		 int birthDay = user.getBirthday().getDayOfMonth();
		 
		 int showMonth = entry.getTime().getMonthOfYear();
		 int birthMonth = user.getBirthday().getMonthOfYear();
		 
		if (showDay == birthDay && showMonth == birthMonth) {
			return 5L;
		} else {
			return 0L;
		}
	}

}
