package com.epam.service;

import com.epam.domain.Show;
import com.epam.domain.User;

public interface DiscountService {
    Long getDiscount(User user, Show show);
}
