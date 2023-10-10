package com.eleven.upms.domain;

import com.eleven.upms.model.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserConvertor {
    private final ModelMapper modelMapper;
    private final AuthorityManager authorityManager;


    public UserDto toDto(User user) {
        var detail = modelMapper.map(user, UserDto.class);
        var owner = Authority.ownerOf(user.toPrincipal());

        // 所有角色
        authorityManager.authoritiesOf(owner, Authority.POWER_ROLE, Authority.POWER_PERMISSION)
                .stream()
                .map(Authority::getPower)
                .map(Authority.Power::getName)
                .forEach(detail.getRoles()::add);

        return detail;
    }
}
