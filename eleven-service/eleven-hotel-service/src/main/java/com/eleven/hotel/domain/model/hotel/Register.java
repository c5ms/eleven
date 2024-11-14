package com.eleven.hotel.domain.model.hotel;

import com.eleven.core.domain.DomainValidator;
import com.eleven.hotel.domain.errors.HotelErrors;
import com.eleven.hotel.api.domain.model.RegisterState;
import com.eleven.hotel.domain.core.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

@Table(name = "hms_hotel_register")
@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldNameConstants
public class Register extends AbstractEntity {

    @Id
    @Column(name = "register_id")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = GENERATOR_NAME)
    private Long registerId;

    @Column(name = "hotel_id")
    private Long hotelId;

    @Embedded
    private HotelInformation hotel;

    @Embedded
    private AdminInformation admin;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private RegisterState state;

    public Register(HotelInformation hotel, AdminInformation admin) {
        this.setHotel(hotel);
        this.setAdmin(admin);
        this.setState(RegisterState.UNDER_REVIEW);
    }

    public void reject() {
        this.isReviewable();
        this.state = RegisterState.REJECTED;
    }

    public void accept() {
        this.isReviewable();
        this.state = RegisterState.ACCEPTED;
    }

    private void isReviewable() {
        DomainValidator.must(this.state == RegisterState.UNDER_REVIEW, HotelErrors.REGISTER_NOT_REVIEWABLE);
    }

    public void belongTo(Hotel hotel) {
        this.setHotelId(hotel.getHotelId());
    }

    @Getter
    @Embeddable
    @FieldNameConstants
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class AdminInformation {

        @Column(name = "admin_name")
        private String name;

        @Column(name = "admin_email")
        private String email;

        @Column(name = "admin_tel")
        private String tel;

    }

    @Getter
    @Embeddable
    @FieldNameConstants
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class HotelInformation {

        @Column(name = "hotel_name")
        private String name;

        @Column(name = "hotel_address")
        private String address;

    }


}
