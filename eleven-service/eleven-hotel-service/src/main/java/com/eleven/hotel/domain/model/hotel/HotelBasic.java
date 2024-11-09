package com.eleven.hotel.domain.model.hotel;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.time.LocalTime;

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

    @Column(name = "hotel_head_pic_url")
    private String headPicUrl;

    @Column(name = "hotel_total_rooms")
    private Integer totalRooms;

    @Column(name = "check_in_time")
    private LocalTime checkInTime;

    @Column(name = "check_out_time")
    private LocalTime checkOutTime;

    @Column(name = "contact_email")
    private String email;

    @Column(name = "contact_tel")
    private String tel;

    public static HotelBasic justName(String name) {
        var basic = new HotelBasic();
        basic.setName(name);
        return basic;
    }
}
