package com.demcia.eleven.domain.upms.service;

import com.demcia.eleven.core.exception.DataNotFoundException;
import com.demcia.eleven.core.pageable.PageResult;
import com.demcia.eleven.domain.upms.action.UserQueryAction;
import com.demcia.eleven.domain.upms.action.UserUpdateAction;
import com.demcia.eleven.domain.upms.entity.User;

import java.util.Optional;

public interface UserService {
    void saveUser(User user);

    Optional<User> getUser(String id);

    void updateUser(User user, UserUpdateAction action);

    PageResult<User> queryUser(UserQueryAction queryAction);

    default User requireUser(String id) {
        return this.getUser(id).orElseThrow(() -> new DataNotFoundException("用户不存在"));
    }

}
