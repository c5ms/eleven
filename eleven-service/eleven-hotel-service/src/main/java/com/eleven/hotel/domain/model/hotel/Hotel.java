package com.eleven.hotel.domain.model.hotel;

import com.eleven.hotel.domain.core.AbstractEntity;
import com.eleven.hotel.domain.values.Address;
import com.eleven.hotel.domain.values.CheckPolicy;
import com.eleven.hotel.domain.values.Position;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Table(name = "hotel")
@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@FieldNameConstants
public class Hotel extends AbstractEntity {

    @Id
    @Column(name = "hotel_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hotelId;

    @Column(name = "active")
    private Boolean active = true;

    @Setter
    @Embedded
    private HotelBasic basic = HotelBasic.empty();

    @Setter
    @Embedded
    private Address address = Address.empty();

    @Setter
    @Embedded
    private Position position = Position.empty();

    @Setter
    @Embedded
    private CheckPolicy checkPolicy = CheckPolicy.empty();

    protected Hotel() {
    }

    @Builder
    public Hotel(CheckPolicy checkPolicy, Position position, Address address, HotelBasic basic) {
        this.checkPolicy = checkPolicy;
        this.position = position;
        this.address = address;
        this.basic = basic;
        this.active();
    }

    public void deactivate() {
        this.active = false;
    }

    public void active() {
        this.active = true;
    }
}
