package com.eleven.hotel.domain.model.hotel;

import com.eleven.hotel.api.domain.model.SaleState;
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
@FieldNameConstants
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hotel implements Saleable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "hms_generator")
    @TableGenerator(name = "hms_generator", table = "hms_sequences")
    private Integer id;

    @NonNull
    @Column(name = "sale_state")
    @Enumerated(EnumType.STRING)
    private SaleState saleState = SaleState.STOPPED;

    @Embedded
    private Position position;

    @Setter
    @Embedded
    private Description description;

    public Hotel(Register register) {
        var description = new Description(register.getHotel().getName());
        this.setDescription(description);
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

    @Nonnull
    public Position getPosition() {
        return Optional.ofNullable(position).orElse(new Position());
    }

    public Description getDescription() {
        return Optional.ofNullable(description).orElse(new Description());
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

        public Description(String name) {
            this.name = name;
        }

    }
}
