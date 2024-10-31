package com.eleven.hotel.domain.model.hotel;

import com.eleven.hotel.api.domain.model.SaleState;
import com.eleven.hotel.domain.core.AbstractEntity;
import com.eleven.hotel.domain.core.Saleable;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.time.LocalTime;
import java.util.Optional;

@Table(name = "hms_hotel")
@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldNameConstants
public class Hotel extends AbstractEntity implements Saleable {

    @NonNull
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

    @Override
    public void startSale() {
        this.saleState = SaleState.STARTED;
    }

    @Override
    public void stopSale() {
        this.saleState = SaleState.STOPPED;
    }

    @Override
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

    @Embeddable
    @Getter
    @Setter(AccessLevel.PROTECTED)
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @FieldNameConstants
    public static class HotelBasic {

        @Column(name = "hotel_name", nullable = false)
        private String name;

        @Column(name = "hotel_description")
        private String description;

        @Column(name = "hotel_head_pic_url")
        private String headPicUrl;

        @Column(name = "hotel_total_rooms")
        private Integer totalRooms;

        @Column(name = "check_in_time")
        private LocalTime checkInTime;

        @Column(name = "check_out_time")
        private LocalTime checkOutTime;

        @Column(name = "contact_email")
        private String email;

        @Column(name = "contact_tel")
        private String tel;

        public static HotelBasic justName(String name) {
            var basic = new HotelBasic();
            basic.setName(name);
            return basic;
        }

    }

    @Embeddable
    @Getter
    @Setter(AccessLevel.PROTECTED)
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @FieldNameConstants
    public static class HotelPosition {

        @Column(name = "position_province")
        private String province;

        @Column(name = "position_city")
        private String city;

        @Column(name = "position_district")
        private String district;

        @Column(name = "position_street")
        private String street;

        @Column(name = "position_address")
        private String address;

        @Column(name = "position_lat")
        private Double lat;

        @Column(name = "position_lng")
        private Double lng;
    }
}
