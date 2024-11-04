package com.eleven.booking.domain.model.hotel;

import com.eleven.hotel.api.domain.model.SaleState;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Table(name = "hms_hotel")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldNameConstants
public class HotelInfo {

    @Id
    @Column(name = "hotel_id")
    private Long hotelId;

    @Column(name = "hotel_name", nullable = false)
    private String name;

    @Nonnull
    @Column(name = "sale_state")
    @Enumerated(EnumType.STRING)
    private SaleState saleState = SaleState.STOPPED;

}
