package com.motiveschina.hotel.features.hotel.values;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Embeddable
@Getter
@FieldNameConstants
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class Position implements Serializable {

    @Column(name = "position_lat")
    private Double latitude;

    @Column(name = "position_lng")
    private Double longitude;

    public static Position empty() {
        return new Position();
    }

    public static Position of(Double latitude, Double longitude) {
        return new Position(latitude, longitude);
    }
}
