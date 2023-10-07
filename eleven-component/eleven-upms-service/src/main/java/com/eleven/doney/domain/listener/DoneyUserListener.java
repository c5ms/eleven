package com.eleven.doney.domain.listener;

import com.eleven.doney.domain.MemberRepository;
import com.eleven.doney.domain.MemberRoleRepository;
import com.eleven.upms.event.UserDeletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DoneyUserListener {
    private final MemberRepository memberRepository;
    private final MemberRoleRepository memberRoleRepository;

    @EventListener
    void on(UserDeletedEvent event) {
        var userId = event.userId();
        memberRepository.deleteAllByUserId(userId);
        memberRoleRepository.deleteAllByUserId(userId);
    }
}
