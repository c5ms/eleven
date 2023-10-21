package com.eleven.doney.model;

import com.eleven.upms.model.UserSummary;
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

    private static class User{

    }
}
