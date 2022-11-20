package com.eleven.upms.domain.manager;

import com.eleven.upms.domain.model.User;

public interface UserValidator {
    void validate(User user);
}
