package com.demcia.eleven.upms.domain.convertor;

import com.demcia.eleven.upms.core.dto.UserDto;
import com.demcia.eleven.upms.domain.entity.User;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserConverter {
    private final MapperFacade mapperFacade;

    public UserDto toDto(User user) {
        var userDto = mapperFacade.map(user, UserDto.class);
        // do something else ...
        return userDto;
    }

}
