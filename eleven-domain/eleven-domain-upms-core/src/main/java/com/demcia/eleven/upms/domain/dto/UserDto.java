package com.demcia.eleven.upms.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;


@Data
public class UserDto implements Serializable {


    private String id;
    private String username;

}