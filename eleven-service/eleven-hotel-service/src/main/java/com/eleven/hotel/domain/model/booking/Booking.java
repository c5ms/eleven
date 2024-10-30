package com.eleven.hotel.domain.model.booking;

import com.eleven.core.data.AbstractEntity;
import com.eleven.hotel.api.domain.error.HotelErrors;
import com.eleven.hotel.domain.model.coupon.Coupon;
import com.eleven.hotel.domain.model.coupon.CouponCalculator;
import com.eleven.hotel.domain.model.hotel.Hotel;
import com.eleven.hotel.domain.model.hotel.Room;
import com.eleven.hotel.domain.model.hotel.Plan;
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
public class Booking extends AbstractEntity {

    @Column("hotel_id")
    private final Hotel hotel;
    @Column("plan_id")
    private final Plan plan;
    @Column("room_id")
    private final Room room;
    @Column("traveler_id")
    private final Traveler traveler;
    @Embedded.Empty(prefix = "stay_")
    private final DateRange stayPeriod;
    @Id
    @Column("booking_id")
    private String id;
    @Embedded.Empty
    private Price price;

    @MappedCollection
    private Set<String> coupons;

    public Booking(String id, Hotel hotel, Plan plan, Room room, DateRange stayPeriod, Traveler traveler) {
        this.id = id;
        this.hotel = hotel;
        this.plan = plan;
        this.room = room;
        this.traveler = traveler;
        this.stayPeriod = stayPeriod;
        this.price = plan.chargeRoom(room)
            .map(price -> price.multiply(stayPeriod))
            .orElseThrow(HotelErrors.BOOKING_NO_SUCH_ROOM::toException);
    }

    public void applyCoupon(List<Coupon> coupons) {
        var couponCalculator = new CouponCalculator(price, coupons);
        this.price = couponCalculator.calcFinalPrice();
        this.coupons = coupons.stream().map(Coupon::getCode).collect(Collectors.toSet());
    }


}
