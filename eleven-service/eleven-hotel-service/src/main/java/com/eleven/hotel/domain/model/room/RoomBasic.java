package com.eleven.hotel.domain.model.room;

import com.eleven.hotel.api.domain.model.RoomLevel;
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

    @Column(name = "room_level")
    @Enumerated(EnumType.STRING)
    private RoomLevel level;

    @Column(name = "room_desc")
    private String desc;

    @Column(name = "room_pic_url")
    private String headPicUrl;

    public RoomBasic(String name, RoomLevel level, String desc, String headPicUrl) {
        Validate.notNull(name, "name must not null");
        Validate.notNull(level, "level must not null");
        this.name = name;
        this.level = level;
        this.desc = desc;
        this.headPicUrl = headPicUrl;
    }
}
