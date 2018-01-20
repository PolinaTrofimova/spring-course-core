package com.epam.service.discount;

import com.epam.domain.Show;
import com.epam.domain.User;

public interface DiscountStrategy {
    public Long execute(User user, Show show);
}
