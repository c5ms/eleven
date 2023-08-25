package com.eleven.upms.domain;

import com.eleven.upms.model.PowerDto;
import com.eleven.upms.model.RoleDto;
import com.eleven.upms.model.UserDetail;
import com.eleven.upms.model.UserDto;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpmsConvertor {

    private final MapperFacade mapperFacade;
    private final AuthorityRepository authorityRepository;

    public RoleDto toDto(Role role) {
        return mapperFacade.map(role, RoleDto.class);
    }

    public PowerDto toDto(Authority.Power power) {
        return mapperFacade.map(power, PowerDto.class);
    }

    public UserDto toDto(User user) {
        return mapperFacade.map(user, UserDto.class);
    }

    public UserDetail toDetail(User user) {
        var detail = mapperFacade.map(user, UserDetail.class);
        var owner = Authority.ownerOfUser(user.getId());
        var authorities = authorityRepository.findByOwner(owner);

        var roles = authorities.stream()
                .filter(authority -> authority.isStuffTypeBy(Authority.POWER_ROLE))
                .map(Authority::getPower)
                .map(Authority.Power::getName)
                .toList();
        detail.setRoles(roles);

        return detail;
    }

}
