package com.eleven.hotel.domain.values;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.time.YearMonth;

@Embeddable
@Getter
@FieldNameConstants
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class HotelBasic implements Serializable {

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


    public static HotelBasic of(String name,
                                String description,
                                String email,
                                String phone,
                                Integer totalRoomQuantity,
                                YearMonth whenBuilt,
                                YearMonth lastRenovation,
                                Integer starRating,
                                Integer buildingArea) {
        return new HotelBasic(name, description, email, phone, totalRoomQuantity, whenBuilt, lastRenovation, starRating, buildingArea);
    }
}
