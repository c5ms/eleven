package com.eleven.hotel.domain.model.hotel;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.time.YearMonth;

@Embeddable
@Getter
@Setter(AccessLevel.PROTECTED)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldNameConstants
public class HotelBasic implements Serializable {

    @Column(name = "hotel_name", nullable = false)
    private String name;

    @Column(name = "hotel_description")
    private String description;

    @Column(name = "contact_email")
    private String email;

    @Column(name = "contact_phone")
    private String phone;

    @Column(name = "total_room_quantity")
    private Integer totalRoomQuantity;

    @Column(name = "when_built")
    private YearMonth whenBuilt;

    @Column(name = "last_renovation")
    private YearMonth lastRenovation;

    @Column(name = "star_rating")
    private Integer starRating;

    @Column(name = "building_area")
    private Integer buildingArea;

    public static HotelBasic empty() {
        return new HotelBasic();
    }
}
