package com.eleven.hotel.domain.model.hotel;

import com.eleven.core.data.AbstractEntity;
import com.eleven.core.domain.DomainHelper;
import com.eleven.hotel.api.domain.error.HotelErrors;
import com.eleven.hotel.api.domain.model.RegisterState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "register")
@Getter
@FieldNameConstants
@AllArgsConstructor(onConstructor = @__({@PersistenceCreator}))
public class Register extends AbstractEntity {

    public static final String DOMAIN_NAME = "Register";


    @Id
    private String id;

    @Column("hotel_id")
    private String hotelId;

    @Column("register_id")
    private String registerId;

    @Embedded.Empty(prefix = "hotel_")
    private HotelInformation hotel;

    @Embedded.Empty(prefix = "admin_")
    private AdminInformation admin;

    @Column(value = "state")
    private RegisterState state;

    Register(String registerId) {
        this.id = DomainHelper.nextId();
        this.registerId = registerId;
        this.state = RegisterState.UNDER_REVIEW;
    }

    public static Register of(String registerId, HotelInformation hotel, AdminInformation admin) {
        var register = new Register(registerId);
        register.hotel = hotel;
        register.admin = admin;
        return register;
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
        this.hotelId = hotel.getHotelId();
    }

    @Getter
    @FieldNameConstants
    @AllArgsConstructor(onConstructor = @__({@PersistenceCreator}))
    public static class AdminInformation {

        @Column(value = "name")
        private String name;

        @Column(value = "email")
        private String email;

        @Column(value = "tel")
        private String tel;

    }

    @Getter
    @FieldNameConstants
    @AllArgsConstructor(onConstructor = @__({@PersistenceCreator}))
    public static class HotelInformation {

        @Column("name")
        private String name;

        @Column("address")
        private String address;

    }


}
