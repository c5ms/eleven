package com.eleven.upms.domain;

import cn.hutool.core.bean.BeanUtil;
import com.eleven.upms.model.RoleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleConvertor {


    public RoleDto toDto(Role role) {
        return BeanUtil.toBean(role, RoleDto.class);
    }


}
