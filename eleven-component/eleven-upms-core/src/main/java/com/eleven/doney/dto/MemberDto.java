package com.eleven.doney.dto;

import com.eleven.upms.dto.UserSummary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    private String id;

    private UserSummary user;

    private List<MemberRoleDto> roles = new ArrayList<>();
}
