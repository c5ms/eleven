package com.eleven.hotel.domain.values;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.relational.core.mapping.Column;

@Getter
@Builder
@FieldNameConstants
public class Contact {

    @Column(value = "email")
    private String email;

    @Column(value = "tel")
    private String tel;

    public static Contact of(String email, String tel) {
        return new Contact(email, tel);
    }
}
