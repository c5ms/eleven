package com.eleven.hotel.domain.model.hotel;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;

@Embeddable
@Getter
@Setter(AccessLevel.PROTECTED)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldNameConstants
public class HotelPosition implements Serializable {

    @Column(name = "position_province")
    private String province;

    @Column(name = "position_city")
    private String city;

    @Column(name = "position_district")
    private String district;

    @Column(name = "position_street")
    private String street;

    @Column(name = "position_address")
    private String address;

    @Column(name = "position_lat")
    private Double lat;

    @Column(name = "position_lng")
    private Double lng;
}
