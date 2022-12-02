package com.demcia.eleven.upms.domain.service;

import com.demcia.eleven.core.exception.DataNotFoundException;
import com.demcia.eleven.core.pageable.PaginationResult;
import com.demcia.eleven.upms.core.action.UserCreateAction;
import com.demcia.eleven.upms.core.action.UserQueryAction;
import com.demcia.eleven.upms.core.action.UserUpdateAction;
import com.demcia.eleven.upms.domain.entity.User;

import java.util.Optional;

public interface UserService {

    // ======================== read ========================

    PaginationResult<User> queryUser(UserQueryAction queryAction);

    Optional<User> getUser(String id);

    default User requireUser(String id) {
        return this.getUser(id).orElseThrow(() -> DataNotFoundException.of("用户不存在"));
    }

    // ======================== write ========================

    User createUser(UserCreateAction action);

    void updateUser(User user, UserUpdateAction action);

    void deleteUser(User user);
}
