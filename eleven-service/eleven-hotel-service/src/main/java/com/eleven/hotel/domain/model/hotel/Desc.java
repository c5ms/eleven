package com.eleven.hotel.domain.model.hotel;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalTime;

@Getter
@Builder
@FieldNameConstants
public class Desc {

    @Column(value = "description")
    private String description;

    @Column(value = "head_pic_url")
    private String headPicUrl;

    @Column(value = "room_number")
    private Integer roomNumber;

    @Column(value = "check_in_time")
    private LocalTime checkInTime;

    @Column(value = "check_out_time")
    private LocalTime checkOutTime;

}
