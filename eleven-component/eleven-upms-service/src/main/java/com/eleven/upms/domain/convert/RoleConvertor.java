package com.eleven.upms.domain.convert;

import com.eleven.upms.domain.model.Role;
import com.eleven.upms.core.model.RoleDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoleConvertor {
    private final ModelMapper modelMapper;

    public RoleDetail toDto(Role role) {
        return modelMapper.map(role, RoleDetail.class);
    }


}
