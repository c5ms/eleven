package com.eleven.travel.domain.booking;

import cn.hutool.core.date.DateRange;
import com.eleven.travel.core.SaleChannel;
import com.eleven.travel.core.support.DomainEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;

@Table(name = "booking")
@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@FieldNameConstants
public class Booking extends DomainEntity {

    @Id
    @Column(name = "booking_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @Column(name = "traveler_id")
    private Long travelerId;

    @JoinColumn(name = "hotel_id")
    private Long hotelId;

    @Column(name = "plan_id")
    private Long planId;

    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "person_count", precision = 10)
    private Integer personCount;

    @Enumerated
    @Column(name = "sale_channel", precision = 10)
    private SaleChannel saleChannel;

    @Embedded
    @AttributeOverride(name = "start", column = @Column(name = "check_in_date"))
    @AttributeOverride(name = "end", column = @Column(name = "check_out_date"))
    private DateRange stayPeriod;

    @Column(name = "amount")
    private BigDecimal amount;

    protected Booking() {

    }

    public Booking(Plan plan, Integer personCount, SaleChannel saleChannel, DateRange stayPeriod) {
        this.hotelId = plan.getHotelId();
        this.planId = plan.getPlanId();
        this.roomId = plan.getRoomId();
        this.personCount = personCount;
        this.stayPeriod = stayPeriod;
        this.saleChannel = saleChannel;
        this.amount = plan.charge(saleChannel, personCount);
    }

}
