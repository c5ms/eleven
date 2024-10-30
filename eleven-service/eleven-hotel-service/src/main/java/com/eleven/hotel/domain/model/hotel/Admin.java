package com.eleven.hotel.domain.model.hotel;

import com.eleven.core.data.AbstractEntity;
import lombok.AllArgsConstructor;
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
    private Description description;

    Admin(String id, String hotelId) {
        this.id = id;
        this.hotelId = hotelId;
    }

    public   static Admin of(String id, String hotelId, Description description) {
        var admin = new Admin(id, hotelId);
        admin.description = description;
        return admin;
    }

    @Getter
    @AllArgsConstructor
    @FieldNameConstants
    public static class Description {

        @Column(value = "name")
        private String name;

        @Column(value = "email")
        private String email;

        @Column(value = "tel")
        private String tel;
    }
}
