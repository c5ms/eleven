package com.eleven.upms.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Data
@Accessors(chain = true)
@Schema(description = "用户摘要", name = "UserSummary")
@AllArgsConstructor
@NoArgsConstructor
public class UserSummary implements Serializable {
    private String id;
    private String username;
    private String nickname;
}
