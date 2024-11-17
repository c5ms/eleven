package com.eleven.booking.domain.model.booking;

import com.eleven.booking.domain.core.AbstractEntity;
import com.eleven.hotel.api.domain.values.DateRange;
import com.eleven.hotel.api.domain.values.SaleChannel;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;

@Table(name = "bk_booking")
@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@FieldNameConstants
public class Booking extends AbstractEntity {

    @Id
    @Column(name = "booking_id")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = GENERATOR_NAME)
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
