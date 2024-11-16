package com.eleven.hotel.domain.values;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;

@Embeddable
@Getter
@Setter(AccessLevel.PROTECTED)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldNameConstants
public class Position implements Serializable {

    @Column(name = "position_lat")
    private Double latitude;

    @Column(name = "position_lng")
    private Double longitude;

    public static Position empty() {
        return new Position();
    }
}
