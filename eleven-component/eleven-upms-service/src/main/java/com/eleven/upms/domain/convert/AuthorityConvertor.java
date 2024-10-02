package com.eleven.upms.domain.convert;

import com.eleven.upms.domain.model.Authority;
import com.eleven.upms.core.model.PowerDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthorityConvertor {

    private final ModelMapper modelMapper;

    public PowerDetail toDto(Authority.Power power) {
        return modelMapper.map(power, PowerDetail.class);
    }
}
