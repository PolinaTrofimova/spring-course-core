package com.epam.service.discount.impl;

import com.epam.domain.Show;
import com.epam.domain.User;
import com.epam.service.discount.DiscountStrategy;

public class TenthTicketDiscountStrategyImpl implements DiscountStrategy {

    public Long execute(User user, Show show) {
        int size = user.getTickets().size();
        if (size % 10 == 0 && size > 0) {
            return 50L;
        } else {
            return 0L;
        }
    }

}
