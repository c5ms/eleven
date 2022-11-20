package com.demcia.eleven.upms.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link User} entity
 */
@Data
public class UserDto implements Serializable {
    private   String id;
    private   String username;
}