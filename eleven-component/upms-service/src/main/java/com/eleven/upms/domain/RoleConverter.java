package com.eleven.upms.domain;

import com.eleven.upms.model.RoleDto;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleConverter {
    private final MapperFacade mapperFacade;


}
