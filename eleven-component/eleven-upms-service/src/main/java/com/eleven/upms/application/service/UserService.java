package com.eleven.upms.application.service;

import com.eleven.core.domain.PaginationResult;
import com.eleven.upms.core.UpmsConstants;
import com.eleven.upms.core.command.UserCreateCommand;
import com.eleven.upms.core.command.UserQueryCommand;
import com.eleven.upms.core.command.UserUpdateCommand;
import com.eleven.upms.core.model.UserDetail;
import com.eleven.upms.domain.service.UserManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserManager userManager;

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public PaginationResult<UserDetail> queryUser(UserQueryCommand command) {
        return userManager.query(command);
    }

    @Cacheable(cacheNames = UpmsConstants.CACHE_NAME_USER, key = "#uid+'.detail'")
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Optional<UserDetail> getUser(String uid) {
        return userManager.get(uid);
    }

    @CacheEvict(cacheNames = UpmsConstants.CACHE_NAME_USER, key = "#result.id+'.detail'")
    @Transactional(rollbackFor = Exception.class)
    public UserDetail createUser(UserCreateCommand command) {
        return userManager.create(command);
    }

    @CacheEvict(cacheNames = UpmsConstants.CACHE_NAME_USER, key = "#result.id+'.detail'")
    @Transactional(rollbackFor = Exception.class)
    public UserDetail updateUser(String uid, UserUpdateCommand command) {
        return userManager.update(uid, command);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(String uid) {
        userManager.delete(uid);
    }

}
