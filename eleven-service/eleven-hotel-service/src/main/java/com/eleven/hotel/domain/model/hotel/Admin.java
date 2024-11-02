package com.eleven.hotel.domain.model.hotel;

import com.eleven.hotel.domain.core.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;


@Table(name = "hms_admin")
@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldNameConstants
public class Admin extends AbstractEntity {

    @Id
    @Column(name = "admin_id")
    @GeneratedValue(strategy = GenerationType.TABLE,generator = GENERATOR_NAME)
    private Integer adminId;

    @Column(name = "hotel_id")
    private Integer hotelId;

    @Embedded
    private Description description;

    public Admin(Integer hotelId, Description description) {
        this.setHotelId(hotelId);
        this.setDescription(description);
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldNameConstants
    public static class Description {

        @Column(name = "name")
        private String name;

        @Column(name = "email")
        private String email;

        @Column(name = "tel")
        private String tel;
    }
}
