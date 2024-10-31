package com.eleven.hotel.domain.model.hotel;

import com.eleven.core.domain.DomainHelper;
import com.eleven.hotel.api.domain.error.HotelErrors;
import com.eleven.hotel.api.domain.model.RegisterState;
import com.eleven.hotel.domain.core.AbstractEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Table(name = "hms_register")
@Entity
@Getter
@FieldNameConstants
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Register extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "hms_generator")
    @TableGenerator(name = "hms_generator", table = "hms_sequences")
    private Integer id;

    @Column(name = "hotel_id")
    private Integer hotelId;

    @Embedded
    private HotelInformation hotel;

    @Embedded
    private AdminInformation admin;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private RegisterState state;

    public Register(HotelInformation hotel, AdminInformation admin) {
        this.hotel = hotel;
        this.admin = admin;
        this.state = RegisterState.UNDER_REVIEW;
    }

    public void reject() {
        this.checkBeforeReview();
        this.state = RegisterState.REJECTED;
    }

    public void accept() {
        this.checkBeforeReview();
        this.state = RegisterState.ACCEPTED;
    }

    private void checkBeforeReview() {
        DomainHelper.must(this.state == RegisterState.UNDER_REVIEW, HotelErrors.REGISTRATION_NOT_REVIEWABLE);
    }

    public void belongTo(Hotel hotel) {
        this.hotelId = hotel.getId();
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
