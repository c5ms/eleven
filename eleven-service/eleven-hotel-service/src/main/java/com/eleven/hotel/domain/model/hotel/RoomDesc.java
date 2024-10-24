package com.eleven.hotel.domain.model.hotel;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.relational.core.mapping.Column;

@Getter
@Builder
public class RoomDesc {

    @Column(value = "desc")
    private String desc;

    @Column(value = "head_pic_url")
    private String headPicUrl;

}
