package com.eleven.doney.domain;

import com.eleven.core.domain.BeanConvertor;
import com.eleven.doney.model.MemberDto;
import com.eleven.upms.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberConvertor {

    private final BeanConvertor beanConvertor;
    private final UserRepository userRepository;

    public MemberDto toDto(Member member) {
        MemberDto dto = beanConvertor.to(member, MemberDto.class);

        userRepository.findSummaryById(member.getUserId())
                        .ifPresent(dto::setUser);

        return dto;
    }


}
