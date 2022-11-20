package com.eleven.upms.application.convert;

import com.eleven.upms.api.application.model.PowerDetail;
import com.eleven.upms.domain.model.Authority;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthorityConvertor {

    private final ModelMapper modelMapper;

    public PowerDetail toDetail(Authority.Power power) {
        return modelMapper.map(power, PowerDetail.class);
    }
}
