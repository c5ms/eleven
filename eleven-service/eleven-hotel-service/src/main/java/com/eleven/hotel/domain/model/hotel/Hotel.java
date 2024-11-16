package com.eleven.hotel.domain.model.hotel;

import com.eleven.hotel.domain.core.AbstractEntity;
import com.eleven.hotel.domain.values.Address;
import com.eleven.hotel.domain.values.CheckPolicy;
import com.eleven.hotel.domain.values.Position;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "hotel")
@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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


    @Embedded
    private Address address = Address.empty();

    @Setter
    @Embedded
    private Position position = Position.empty();

    @Embedded
    private CheckPolicy checkPolicy = CheckPolicy.empty();

    @Builder
    public Hotel(HotelBasic basic, Position position, Address address, CheckPolicy checkPolicy) {
        this.basic = basic;
        this.position = position;
        this.address = address;
        this.checkPolicy = checkPolicy;
    }

    public void deactivate() {
        this.active=false;
    }

    public void active() {
        this.active=true;
    }
}
