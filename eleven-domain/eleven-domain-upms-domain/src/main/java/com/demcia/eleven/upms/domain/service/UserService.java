package com.demcia.eleven.upms.domain.service;

import com.demcia.eleven.core.exception.DataNotFoundException;
import com.demcia.eleven.core.pageable.PaginationResult;
import com.demcia.eleven.core.pageable.Pagination;
import com.demcia.eleven.upms.core.action.UserCreateAction;
import com.demcia.eleven.upms.core.action.UserUpdateAction;
import com.demcia.eleven.upms.domain.entity.User;
import com.demcia.eleven.upms.core.action.UserQueryAction;

import java.util.Optional;

public interface UserService {

    // ======================== read ========================
    Optional<User> getUser(String id);

    PaginationResult<User> queryUser(UserQueryAction queryAction, Pagination pagination);

    default User requireUser(String id) {
        return this.getUser(id).orElseThrow(() -> new DataNotFoundException("用户不存在"));
    }

    // ======================== write ========================

    User createUser(UserCreateAction action);

    void updateUser(User user, UserUpdateAction action);

    void deleteUser(User user);
}
