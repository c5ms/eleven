package com.eleven.hotel.domain.model.hotel;

import com.eleven.hotel.api.domain.model.SaleState;
import com.eleven.hotel.domain.core.AbstractEntity;
import com.eleven.hotel.domain.core.Saleable;
import com.eleven.hotel.domain.model.hotel.event.HotelClosed;
import com.eleven.hotel.domain.model.hotel.event.HotelOpened;
import com.eleven.hotel.domain.model.hotel.event.HotelRelocated;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.util.Optional;

@Table(name = "hms_hotel")
@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldNameConstants
public class Hotel extends AbstractEntity implements Saleable {

    @Id
    @Column(name = "hotel_id")
    @GeneratedValue(strategy = GenerationType.TABLE,generator = GENERATOR_NAME)
    private Long hotelId;

    @Nonnull
    @Column(name = "sale_state")
    @Enumerated(EnumType.STRING)
    private SaleState saleState = SaleState.STOPPED;

    @Embedded
    private HotelPosition position;

    @Setter
    @Embedded
    private HotelBasic basic;

    public Hotel(Register register) {
        var description = HotelBasic.justName(register.getHotel().getName());
        this.setBasic(description);
    }

    public Hotel(HotelBasic basic, HotelPosition position) {
        this.setBasic(basic);
        this.setPosition(position);
    }

    @Override
    public void startSale() {
        if(this.saleState!=SaleState.STARTED){
            this.saleState = SaleState.STARTED;
            addEvent(new HotelOpened(this));
        }
    }

    @Override
    public void stopSale() {
        this.saleState = SaleState.STOPPED;
        addEvent(new HotelClosed(this));
    }

    @Override
    public boolean isOnSale() {
        return saleState.isOnSale();
    }

    public void relocate(HotelPosition position) {
        this.position = position;
        this.addEvent(new HotelRelocated(this));
    }

    @Nonnull
    public HotelPosition getPosition() {
        return Optional.ofNullable(position).orElse(new HotelPosition());
    }

    public HotelBasic getBasic() {
        return Optional.ofNullable(basic).orElse(new HotelBasic());
    }

}
