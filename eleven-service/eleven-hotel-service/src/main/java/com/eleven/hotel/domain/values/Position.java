package com.eleven.hotel.domain.values;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.relational.core.mapping.Column;

@Getter
@Builder
@FieldNameConstants
public class Position {

    @Column(value = "province")
    private String province;

    @Column(value = "city")
    private String city;

    @Column(value = "district")
    private String district;

    @Column(value = "street")
    private String street;

    @Column(value = "address")
    private String address;

    @Column(value = "lat")
    private Double lat;

    @Column(value = "lng")
    private Double lng;

}
