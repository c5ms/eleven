package com.demcia.eleven.upms.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;

@Schema(description = "用户权限")
@Data
public class UserAuthorityDto {
    @Column("name_")
    private String name;

    @Column("type_")
    private String type;
}
