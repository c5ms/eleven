package com.eleven.upms.domain;

import cn.hutool.core.bean.BeanUtil;
import com.eleven.upms.model.PowerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorityConvertor {
    public PowerDto toDto(Authority.Power power) {
        return BeanUtil.toBean(power, PowerDto.class);
    }
}
