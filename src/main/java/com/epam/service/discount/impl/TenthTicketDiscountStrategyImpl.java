package com.epam.service.discount.impl;

import com.epam.domain.Show;
import com.epam.domain.User;
import com.epam.service.discount.DiscountStrategy;

public class TenthTicketDiscountStrategyImpl implements DiscountStrategy {
    private static final Long ZERO = 0L;
    private static final Long FIFTY = 50L;
    private static final int ZERO_INT = 0;
    private static final int PRICE = 10;

    public Long execute(User user, Show show) {
        int size = user.getTickets().size();
        if (size % PRICE == ZERO_INT && size > ZERO_INT) {
            return FIFTY;
        } else {
            return ZERO;
        }
    }
}
