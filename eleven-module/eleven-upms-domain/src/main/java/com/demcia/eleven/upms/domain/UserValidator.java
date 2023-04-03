package com.demcia.eleven.upms.domain;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {
    private final UserRepository userRepository;

    /**
     * 验证用户信息
     *
     * @param user 用户
     */
    public void validate(User user) {
        // 验证，用户名不能重复
        userRepository.findByLogin(user.getLogin())
                .filter(exist-> !StringUtils.equals(exist.getId(),user.getId()))
                .ifPresent(exist -> {
                    throw UserErrors.USER_NAME_REPEAT.reject();
                });
    }


}
