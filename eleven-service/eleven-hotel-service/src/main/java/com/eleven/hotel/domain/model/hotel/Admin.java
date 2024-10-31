package com.eleven.hotel.domain.model.hotel;

import com.eleven.hotel.domain.core.AbstractEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;


@Table(name = "hms_admin")
@Entity
@Getter
@FieldNameConstants
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "hms_generator")
    @TableGenerator(name = "hms_generator", table = "hms_sequences")
    private Integer id;

    @Column(name = "hotel_id")
    private Integer hotelId;

    @Embedded
    private Description description;

    public Admin(Integer hotelId, Description description) {
        this.hotelId = hotelId;
        this.description = description;
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
