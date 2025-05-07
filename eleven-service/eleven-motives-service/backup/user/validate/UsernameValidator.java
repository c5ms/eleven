package com.motiveschina.hotel.features.user.validate;

import com.eleven.domain.user.manager.UserValidator;
import com.eleven.domain.user.model.User;
import com.eleven.domain.user.model.UserRepository;
import com.eleven.upms.api.domain.core.UpmsConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UsernameValidator implements UserValidator {

    private final UserRepository userRepository;

    @Override
    public void validate(User user) {
        var existUser = userRepository.findByUsername(user.getUsername())
                .filter(check -> !StringUtils.equals(check.getId(), user.getId()));
        if (existUser.isPresent()) {
            throw UpmsConstants.ERROR_USER_NAME_REPEAT.toException();
        }
    }
}
