package com.epam.aop.map;

import java.util.HashMap;
import java.util.Map;

public class MetaInfDiscount {
    private long tenthTicketDiscountCounter = 0L;
    private long birthdayDiscountCounter = 0L;

    private Map<String, Long> tenthTicketDiscounts = new HashMap<>();
    private Map<String, Long> birthdayDiscounts = new HashMap<>();


    public Map<String, Long> getTenthTicketDiscounts() {
        return tenthTicketDiscounts;
    }

    public Map<String, Long> getBirthdayDiscounts() {
        return birthdayDiscounts;
    }

    public long getTenthTicketDiscountCounter() {
        return tenthTicketDiscountCounter;
    }

    public long getBirthdayDiscountCounter() {
        return birthdayDiscountCounter;
    }

}
