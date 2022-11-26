package com.demcia.eleven.upms.service;

import com.demcia.eleven.core.exception.DataNotFoundException;
import com.demcia.eleven.core.pageable.PaginationResult;
import com.demcia.eleven.core.pageable.Pagination;
import com.demcia.eleven.upms.core.action.UserCreateAction;
import com.demcia.eleven.upms.core.action.UserUpdateAction;
import com.demcia.eleven.upms.domain.User;
import com.demcia.eleven.upms.core.action.UserQuery;

import java.util.Optional;

public interface UserService {

    // ======================== read ========================
    Optional<User> getUser(Long id);

    PaginationResult<User> queryUser(UserQuery queryAction, Pagination pagination);

    default User requireUser(Long id) {
        return this.getUser(id).orElseThrow(() -> new DataNotFoundException("用户不存在"));
    }

    // ======================== write ========================

    User createUser(UserCreateAction action);

    void updateUser(User user, UserUpdateAction action);

}
