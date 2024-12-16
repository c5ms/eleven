package com.eleven.travel.domain.hotel.values;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.time.LocalTime;

@Embeddable
@Getter
@FieldNameConstants
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class CheckPolicy implements Serializable {

    @Column(name = "check_in_time")
    private LocalTime checkInTime;

    @Column(name = "check_out_time")
    private LocalTime checkOutTime;

    public static CheckPolicy empty() {
        return new CheckPolicy();
    }

    public static CheckPolicy of(LocalTime checkInTime, LocalTime checkOutTime) {
        return new CheckPolicy(checkInTime, checkOutTime);
    }
}
