package com.eleven.hotel.domain.model.hotel;

import com.eleven.core.data.AbstractEntity;
import com.eleven.core.domain.DomainUtils;
import com.eleven.hotel.api.domain.core.HotelErrors;
import com.eleven.hotel.api.domain.model.RegisterState;
import com.eleven.hotel.domain.core.HotelAware;
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
public class Register extends AbstractEntity implements HotelAware {

    @Id
    private String id;

    @Column("hotel_id")
    private String hotelId;

    @Column("hotel_name")
    private String hotelName;

    @Column("hotel_address")
    private String hotelAddress;

    @Embedded.Empty(prefix = "manager_")
    private Admin.Contact managerContact;

    @Column(value = "state")
    private RegisterState state;

    private Register(String id) {
        this.id = id;
        this.state = RegisterState.UNDER_REVIEW;
    }

    public static Register create(String id, String hotelName, String hotelAddress,  Admin.Contact managerContact) {
        var register = new Register(id);
        register.hotelName = hotelName;
        register.hotelAddress = hotelAddress;
        register.managerContact = managerContact;
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
        DomainUtils.must(this.state == RegisterState.UNDER_REVIEW, HotelErrors.REGISTRATION_NOT_REVIEWABLE);
    }

    public void belongTo(Hotel hotel) {
        this.hotelId = hotel.getId();
    }
}
