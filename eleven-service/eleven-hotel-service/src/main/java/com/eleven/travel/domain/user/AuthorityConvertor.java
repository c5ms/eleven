package com.eleven.travel.domain.user;

import com.eleven.domain.user.model.Authority;
import com.eleven.upms.api.application.model.PowerDetail;
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
