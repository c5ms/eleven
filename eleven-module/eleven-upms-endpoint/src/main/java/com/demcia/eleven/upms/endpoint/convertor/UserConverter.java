package com.demcia.eleven.upms.endpoint.convertor;

import com.demcia.eleven.upms.domain.User;
import com.demcia.eleven.upms.domain.UserAuthority;
import com.demcia.eleven.upms.domain.UserAuthorityDto;
import com.demcia.eleven.upms.domain.UserDto;
import com.demcia.eleven.upms.domain.action.UserCreateAction;
import com.demcia.eleven.upms.domain.action.UserFilter;
import com.demcia.eleven.upms.domain.action.UserUpdateAction;
import com.demcia.eleven.upms.domain.request.UserCreateRequest;
import com.demcia.eleven.upms.domain.request.UserQueryRequest;
import com.demcia.eleven.upms.domain.request.UserUpdateRequest;
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
