package com.eleven.hotel.domain.model.hotel;

import com.eleven.hotel.api.domain.model.SaleState;
import com.eleven.hotel.domain.core.AbstractEntity;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import java.util.Optional;

@Table(name = "hms_hotel")
@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldNameConstants
public class Hotel extends AbstractEntity  {

    @Id
    @Column(name = "hotel_id")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = GENERATOR_NAME)
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

    public void startSale() {
        if (this.saleState != SaleState.STARTED) {
            this.saleState = SaleState.STARTED;
        }
    }

    public void stopSale() {
        this.saleState = SaleState.STOPPED;
    }

    public boolean isOnSale() {
        return saleState.isOnSale();
    }

    public void relocate(HotelPosition position) {
        this.position = position;
    }

    @Nonnull
    public HotelPosition getPosition() {
        return Optional.ofNullable(position).orElse(new HotelPosition());
    }

    public HotelBasic getBasic() {
        return Optional.ofNullable(basic).orElse(new HotelBasic());
    }

}
