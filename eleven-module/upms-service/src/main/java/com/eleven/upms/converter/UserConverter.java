package com.eleven.upms.converter;

import com.eleven.upms.api.dto.UserAuthorityDto;
import com.eleven.upms.api.dto.UserDto;
import com.eleven.upms.api.request.UserCreateRequest;
import com.eleven.upms.api.request.UserQueryRequest;
import com.eleven.upms.api.request.UserUpdateRequest;
import com.eleven.upms.domain.User;
import com.eleven.upms.domain.UserAuthority;
import com.eleven.upms.domain.UserFilter;
import com.eleven.upms.domain.action.UserCreateAction;
import com.eleven.upms.domain.action.UserUpdateAction;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserConverter {
    private final MapperFacade mapperFacade;

    public UserDto toDto(User user) {
        return mapperFacade.map(user, UserDto.class);
    }

    public UserAuthorityDto toDto(UserAuthority userAuthority) {
        return mapperFacade.map(userAuthority, UserAuthorityDto.class);
    }

    public UserCreateAction toAction(UserCreateRequest request) {
        return mapperFacade.map(request, UserCreateAction.class);
    }

    public UserUpdateAction toAction(UserUpdateRequest request) {
        return mapperFacade.map(request, UserUpdateAction.class);
    }

    public UserFilter toFilter(UserQueryRequest request) {
        return mapperFacade.map(request, UserFilter.class);
    }

}
