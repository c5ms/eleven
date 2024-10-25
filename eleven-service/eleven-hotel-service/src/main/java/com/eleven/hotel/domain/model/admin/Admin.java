package com.eleven.hotel.domain.model.admin;

import com.eleven.core.data.AbstractEntity;
import com.eleven.hotel.domain.model.hotel.Hotel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "admin")
@Getter
@FieldNameConstants
@AllArgsConstructor(onConstructor = @__({@PersistenceCreator}))
public class Admin extends AbstractEntity {

    @Id
    private final String id;

    @Column(value = "hotel_id")
    private final String hotelId;

    @Embedded.Empty
    private Contact contact;

    private Admin(String id, String hotelId) {
        this.id = id;
        this.hotelId = hotelId;
    }

    public static Admin of(String id, String hotelId, Contact contact) {
        var admin = new Admin(id, hotelId);
        admin.contact = contact;
        return admin;
    }

    @Getter
    @AllArgsConstructor
    @FieldNameConstants
    public static class Contact {

        @Column(value = "name")
        private String name;

        @Column(value = "email")
        private String email;

        @Column(value = "tel")
        private String tel;
    }
}
