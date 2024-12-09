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
import lombok.*;
import lombok.experimental.FieldNameConstants;

@Table(name = "hotel")
@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@FieldNameConstants
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
