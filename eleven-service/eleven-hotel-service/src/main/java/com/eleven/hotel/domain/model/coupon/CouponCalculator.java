package com.eleven.hotel.domain.model.coupon;

import com.eleven.hotel.domain.values.Price;

import java.util.List;

public class CouponCalculator {

    private final Price price;

    private final List<Coupon> coupons;

    public CouponCalculator(Price price, List<Coupon> coupons) {
        this.price = price;
        this.coupons = coupons;
    }

    public Price calcFinalPrice() {
        return price;
    }

}
