package com.eleven.upms.domain;

import cn.hutool.core.bean.BeanUtil;
import com.eleven.upms.dto.PowerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthorityConvertor {

    private final ModelMapper modelMapper;

    public PowerDto toDto(Authority.Power power) {
        return modelMapper.map(power, PowerDto.class);
    }
}
