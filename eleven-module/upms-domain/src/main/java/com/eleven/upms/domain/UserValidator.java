package com.eleven.upms.domain;

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
        var existUser = userRepository.findByLogin(user.getLogin())
            .filter(check -> !StringUtils.equals(check.getId(), user.getId()));
        if (existUser.isPresent()) {
            throw UserError.USER_NAME_REPEAT.makeRejectException();
        }
    }


}
