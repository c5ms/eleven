package com.demcia.eleven.domain.upms.convertor;

import com.demcia.eleven.domain.upms.dto.UserDto;
import com.demcia.eleven.domain.upms.entity.User;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserConvertor {
    private final MapperFacade mapperFacade;

    public UserDto toDto(User user) {
        var userDto = mapperFacade.map(user, UserDto.class);
        // do something else ...
        return userDto;
    }

}
