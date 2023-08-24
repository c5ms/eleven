package com.eleven.upms.application;

import com.eleven.upms.domain.User;
import com.eleven.upms.domain.UserAuthority;
import com.eleven.upms.dto.UserAuthorityDto;
import com.eleven.upms.dto.UserDto;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConverter {
    private final MapperFacade mapperFacade;

    public UserDto toDto(User user) {
        return mapperFacade.map(user, UserDto.class);
    }

    public UserAuthorityDto toDto(UserAuthority userAuthority) {
        return mapperFacade.map(userAuthority, UserAuthorityDto.class);
    }

}
