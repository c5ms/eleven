package com.eleven.hotel.domain.model.booking;

import com.eleven.hotel.domain.core.AbstractEntity;
import com.eleven.hotel.domain.values.DateRange;
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
    private Integer bookingId;

    @Column(name = "traveler_id")
    private Integer travelerId;

    @Column(name = "hotel_id")
    private Integer hotelId;

    @Column(name = "plan_id")
    private Integer planId;

    @Column(name = "room_id")
    private Integer roomId;

    @Embedded
    @AttributeOverride(name = "start", column = @Column(name = "check_in_date"))
    @AttributeOverride(name = "end", column = @Column(name = "check_out_date"))
    private DateRange stayPeriod;

    @Embedded
    private BigDecimal price;

}
