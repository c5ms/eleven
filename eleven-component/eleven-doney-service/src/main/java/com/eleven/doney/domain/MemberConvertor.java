package com.eleven.doney.domain;

import com.eleven.core.domain.BeanConvertor;
import com.eleven.doney.model.MemberDto;
import com.eleven.upms.client.UpmsClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberConvertor {

    private final BeanConvertor beanConvertor;
    private final UpmsClient upmsClient;

    public MemberDto toDto(Member member) {
        MemberDto dto = beanConvertor.to(member, MemberDto.class);

        // 1. bulk api
        // 2. sync data to agge -> make materialize view cqrs  -> crud | query
        // 3. fetch every time for one record
        // 4. frontend translate
        // 5. store as cache

        upmsClient.readUserSummary(member.getUserId())
                .ifPresent(dto::setUser);

        return dto;
    }


}
