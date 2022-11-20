package com.eleven.hotel.api.application.model.values;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ContactDto {

    private String tel;

    private String email;
}
