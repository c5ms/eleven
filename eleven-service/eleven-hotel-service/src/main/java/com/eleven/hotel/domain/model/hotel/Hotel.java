package com.eleven.hotel.domain.model.hotel;

import com.eleven.core.domain.DomainHelper;
import com.eleven.hotel.api.domain.model.SaleState;
import com.eleven.hotel.domain.core.Saleable;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.time.LocalTime;
import java.util.Optional;


@Table(name = Hotel.TABLE_NAME)
@Entity
@Getter
@FieldNameConstants
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hotel implements Saleable {

    public static final String TABLE_NAME = "hms_hotel";
    public static final String DOMAIN_NAME = "Hotel";

    @Id
    private String id = DomainHelper.nextId();

    @Column(name = "hotel_id")
    private String hotelId;

    @Column(name = "sale_state")
    @Enumerated(EnumType.STRING)
    private SaleState saleState = SaleState.STOPPED;

    @Nullable
    @Embedded
    private Position position = new Position();

    @Setter
    @Embedded
    private Description description = new Description();

    public static Hotel of( Register register) {
        var description = new Description(register.getHotel().getName());

        var hotel = new Hotel();
        hotel.setDescription(description);

        return hotel;
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

    public void relocate(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return Optional.ofNullable(position).orElse(new Position());
    }

    @Getter
    @Embeddable
    @FieldNameConstants
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Position {

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


    @Getter
    @Embeddable
    @AllArgsConstructor
    @FieldNameConstants
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Description {

        @Column(name = "hotel_name")
        private String name;

        @Column(name = "hotel_description")
        private String description;

        @Column(name = "hotel_head_pic_url")
        private String headPicUrl;

        @Column(name = "hotel_room_number")
        private Integer roomNumber;

        @Column(name = "hotel_check_in_time")
        private LocalTime checkInTime;

        @Column(name = "hotel_check_out_time")
        private LocalTime checkOutTime;

        @Column(name = "contact_email")
        private String email;

        @Column(name = "contact_tel")
        private String tel;

        public Description(String name) {
            this.name = name;
        }

    }
}
