package com.epam.service.impl;

import java.util.List;

import com.epam.domain.Show;
import com.epam.domain.User;
import com.epam.service.discount.DiscountStrategy;
import com.epam.service.DiscountService;

//@Service
public class DiscountServiceImpl implements DiscountService{
	private List<DiscountStrategy> strategies;
	
	public DiscountServiceImpl(List<DiscountStrategy> strategies){
		this.strategies = strategies;
	}
	
	public Long getDiscount(User user, Show show) {
		Long maxDiscount = 0L;
		for (DiscountStrategy strategy : strategies) {
			Long discount = strategy.execute(user, show);
			if (discount > maxDiscount) maxDiscount = discount;
		}
		return maxDiscount;
	}

}
