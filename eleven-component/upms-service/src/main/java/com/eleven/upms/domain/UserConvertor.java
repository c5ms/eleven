package com.eleven.upms.domain;

import cn.hutool.core.bean.BeanUtil;
import com.eleven.upms.model.UserDetail;
import com.eleven.upms.model.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConvertor {

    private final AuthorityRepository authorityRepository;

    public UserDto toDto(User user) {
        var userRecord = BeanUtil.toBean(user, UserDto.class);
        return userRecord;
    }

    public UserDetail toDetail(User user) {
        var detail = BeanUtil.toBean(user, UserDetail.class);
        var owner = Authority.ownerOfUser(user.getId());
        var authorities = authorityRepository.findByOwner(owner);

        var roles = authorities.stream()
                .filter(authority -> authority.isStuffTypeBy(Authority.POWER_ROLE))
                .map(Authority::getPower)
                .map(Authority.Power::getName)
                .toList();
        detail.setRoles(roles);

        return detail;
    }
}
