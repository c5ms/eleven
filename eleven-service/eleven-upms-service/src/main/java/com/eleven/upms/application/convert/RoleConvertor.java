package com.eleven.upms.application.convert;

import com.eleven.upms.api.application.model.RoleDetail;
import com.eleven.upms.domain.model.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoleConvertor {
    private final ModelMapper modelMapper;

    public RoleDetail toDetail(Role role) {
        return modelMapper.map(role, RoleDetail.class);
    }

}
