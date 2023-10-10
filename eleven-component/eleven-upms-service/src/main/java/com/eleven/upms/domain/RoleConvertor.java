package com.eleven.upms.domain;

import com.eleven.upms.model.RoleDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoleConvertor {
    private final ModelMapper modelMapper;

    public RoleDto toDto(Role role) {
        return modelMapper.map(role, RoleDto.class);
    }


}
