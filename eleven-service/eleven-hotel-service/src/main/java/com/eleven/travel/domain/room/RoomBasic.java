package com.eleven.travel.domain.room;

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
public final class RoomBasic implements Serializable {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "area")
    private Integer area;

    @Column(name = "floor")
    private Integer floor;

    public static RoomBasic empty() {
        return new RoomBasic();
    }

    public static RoomBasic of(String name, String description, Integer area, Integer floor) {
        return new RoomBasic(name, description, area, floor);
    }
}
