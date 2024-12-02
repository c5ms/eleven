package com.eleven.hotel.domain.values;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;

@Embeddable
@Getter
@FieldNameConstants
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class Address implements Serializable {

    @Column(name = "addr_country")
    private String country;

    @Column(name = "addr_province")
    private String province;

    @Column(name = "addr_city")
    private String city;

    @Column(name = "addr_location")
    private String location;

    @Column(name = "addr_address")
    private String address;

    public static Address empty() {
        return new Address();
    }

}
