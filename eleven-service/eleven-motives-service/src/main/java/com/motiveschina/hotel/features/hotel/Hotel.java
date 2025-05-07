package com.motiveschina.hotel.features.hotel;

import com.motiveschina.hotel.common.support.DomainEntity;
import com.motiveschina.hotel.features.hotel.event.HotelActiveEvent;
import com.motiveschina.hotel.features.hotel.event.HotelCreatedEvent;
import com.motiveschina.hotel.features.hotel.event.HotelDeactivateEvent;
import com.motiveschina.hotel.features.hotel.event.HotelUpdatedEvent;
import com.motiveschina.hotel.features.hotel.values.Address;
import com.motiveschina.hotel.features.hotel.values.CheckPolicy;
import com.motiveschina.hotel.features.hotel.values.HotelBasic;
import com.motiveschina.hotel.features.hotel.values.Position;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Table(name = "hotel")
@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@FieldNameConstants
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hotel extends DomainEntity {

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

    @Builder
    @SuppressWarnings("unused")
    private static Hotel of(CheckPolicy checkPolicy, Position position, Address address, HotelBasic basic) {
        var hotel = new Hotel();
        hotel.setCheckPolicy(checkPolicy);
        hotel.setPosition(position);
        hotel.setAddress(address);
        hotel.setBasic(basic);
        hotel.setActive(false);
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
        if (!this.active) {
            return;
        }
        this.active = false;
        this.addEvent(HotelDeactivateEvent.of(this));
    }

    public void active() {
        if (this.active) {
            return;
        }
        this.active = true;
        this.addEvent(HotelActiveEvent.of(this));
    }
}
