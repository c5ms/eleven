package com.eleven.hotel.domain.model.hotel.values;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address implements Serializable {

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
