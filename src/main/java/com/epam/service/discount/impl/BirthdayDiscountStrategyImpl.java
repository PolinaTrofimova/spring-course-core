package com.epam.service.discount.impl;

import com.epam.domain.Show;
import com.epam.domain.User;
import com.epam.service.discount.DiscountStrategy;

public class BirthdayDiscountStrategyImpl implements DiscountStrategy {
    private static final Long ZERO = 0L;
    private static final Long FIVE = 5L;

    public Long execute(User user, Show entry) {
        int showDay = entry.getTime().getDayOfMonth();
        int birthDay = user.getBirthday().getDayOfMonth();

        int showMonth = entry.getTime().getMonthOfYear();
        int birthMonth = user.getBirthday().getMonthOfYear();

        if (showDay == birthDay && showMonth == birthMonth) {
            return FIVE;
        } else {
            return ZERO;
        }
    }

}
