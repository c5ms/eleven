package com.eleven.hotel.domain.model.hotel.values;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.time.LocalTime;

@Embeddable
@Getter
@Setter(AccessLevel.PROTECTED)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldNameConstants
public class CheckPolicy {

    @Column(name = "check_in_time")
    private LocalTime checkInTime;

    @Column(name = "check_out_time")
    private LocalTime checkOutTime;

    public static CheckPolicy empty() {
        return new CheckPolicy();
    }
}
