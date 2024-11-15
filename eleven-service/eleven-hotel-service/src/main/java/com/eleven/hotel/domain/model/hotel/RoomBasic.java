package com.eleven.hotel.domain.model.hotel;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.apache.commons.lang3.Validate;

@Getter
@Embeddable
@FieldNameConstants
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class RoomBasic {

    @Column(name = "room_name")
    private String name;

    @Column(name = "room_desc")
    private String desc;

    @Column(name = "room_pic_url")
    private String headPicUrl;

    public RoomBasic(String name,  String desc, String headPicUrl) {
        Validate.notNull(name, "name must not null");
        this.name = name;
        this.desc = desc;
        this.headPicUrl = headPicUrl;
    }
}
