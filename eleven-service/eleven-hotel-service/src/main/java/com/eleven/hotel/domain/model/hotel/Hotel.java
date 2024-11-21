package com.eleven.hotel.domain.model.hotel;

import com.eleven.hotel.domain.core.AbstractEntity;
import com.eleven.hotel.domain.model.hotel.event.HotelActiveEvent;
import com.eleven.hotel.domain.model.hotel.event.HotelCreatedEvent;
import com.eleven.hotel.domain.model.hotel.event.HotelDeactivateEvent;
import com.eleven.hotel.domain.model.hotel.event.HotelUpdatedEvent;
import com.eleven.hotel.domain.values.Address;
import com.eleven.hotel.domain.values.CheckPolicy;
import com.eleven.hotel.domain.values.HotelBasic;
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

    @Embedded
    private HotelBasic basic = HotelBasic.empty();

    @Embedded
    private Address address = Address.empty();

    @Embedded
    private Position position = Position.empty();

    @Embedded
    private CheckPolicy checkPolicy = CheckPolicy.empty();

    protected Hotel() {
    }

    @Builder
    public static Hotel of(CheckPolicy checkPolicy, Position position, Address address, HotelBasic basic) {
        var hotel = new Hotel();
        hotel.setCheckPolicy(checkPolicy);
        hotel.setPosition(position);
        hotel.setAddress(address);
        hotel.setBasic(basic);
        hotel.active();
        hotel.addEvent(HotelCreatedEvent.of(hotel));
        return hotel;
    }

    public void update(HotelPatch patch) {
        this.setBasic(patch.getBasic());
        this.setPosition(patch.getPosition());
        this.setCheckPolicy(patch.getCheckPolicy());
        this.setAddress(patch.getAddress());
        this.addEvent(HotelUpdatedEvent.of(this));
    }

    public void deactivate() {
        this.active = false;
        this.addEvent(HotelDeactivateEvent.of(this));
    }

    public void active() {
        this.active = true;
        this.addEvent(HotelActiveEvent.of(this));
    }
}
