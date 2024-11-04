package com.eleven.booking.domain.model.booking;

import com.eleven.booking.domain.core.AbstractEntity;
import com.eleven.booking.domain.core.DateRange;
import com.eleven.booking.domain.model.hotel.HotelInfo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;

@Table(name = "hms_booking")
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

    @Column(name = "person_count",precision = 10)
    private Integer personCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private HotelInfo hotelInfo;

    @Column(name = "plan_id")
    private Long planId;

    @Column(name = "room_id")
    private Long roomId;

    @Embedded
    @AttributeOverride(name = "start", column = @Column(name = "check_in_date"))
    @AttributeOverride(name = "end", column = @Column(name = "check_out_date"))
    private DateRange stayPeriod;

    @Column(name = "amount")
    private BigDecimal amount;

    protected Booking() {
    }

    public Booking(Long travelerId,
                   HotelInfo hotelInfo,
                   Long planId,
                   Long roomId,
                   Integer personCount,
                   DateRange stayPeriod,
                   BigDecimal amount) {
        this.travelerId = travelerId;
        this.personCount = personCount;
        this.hotelInfo = hotelInfo;
        this.planId = planId;
        this.roomId = roomId;
        this.stayPeriod = stayPeriod;
        this.amount = amount;
    }
}
