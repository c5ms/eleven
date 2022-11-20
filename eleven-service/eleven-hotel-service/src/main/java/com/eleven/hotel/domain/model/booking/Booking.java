package com.eleven.hotel.domain.model.booking;

import com.eleven.core.data.AbstractEntity;
import com.eleven.hotel.api.domain.core.HotelErrors;
import com.eleven.hotel.domain.model.coupon.Coupon;
import com.eleven.hotel.domain.model.coupon.CouponCalculator;
import com.eleven.hotel.domain.model.hotel.Hotel;
import com.eleven.hotel.domain.model.hotel.HotelRoom;
import com.eleven.hotel.domain.model.plan.Plan;
import com.eleven.hotel.domain.model.traveler.Traveler;
import com.eleven.hotel.domain.values.DateRange;
import com.eleven.hotel.domain.values.Price;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Table(name = "booking")
@Getter
@FieldNameConstants
public class Booking extends AbstractEntity  {

    @Id
    @Column("booking_id")
    private String id;

    @Column("hotel_id")
    private final Hotel hotel;

    @Column("plan_id")
    private final Plan plan;

    @Column("room_id")
    private final HotelRoom hotelRoom;

    @Column("traveler_id")
    private final Traveler traveler;

    @Embedded.Empty(prefix = "stay_")
    private final DateRange stayPeriod;

    @Embedded.Empty
    private Price price;

    @MappedCollection
    private Set<String> coupons;

    public Booking(String id, Hotel hotel, Plan plan, HotelRoom hotelRoom, DateRange stayPeriod, Traveler traveler) {
        this.id = id;
        this.hotel = hotel;
        this.plan = plan;
        this.hotelRoom = hotelRoom;
        this.traveler = traveler;
        this.stayPeriod = stayPeriod;
        this.price = plan.chargeRoom(hotelRoom)
                .map(price -> price.multiply(stayPeriod))
                .orElseThrow(HotelErrors.BOOKING_NO_SUCH_ROOM::toException);
    }

    public void applyCoupon(List<Coupon> coupons) {
        var couponCalculator = new CouponCalculator(price, coupons);
        this.price = couponCalculator.calcFinalPrice();
        this.coupons = coupons.stream().map(Coupon::getCode).collect(Collectors.toSet());
    }


}
