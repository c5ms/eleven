package com.demcia.eleven.upms.domain.service.impl;

import com.demcia.eleven.core.domain.helper.PageableQueryHelper;
import com.demcia.eleven.core.exception.ValidateFailureException;
import com.demcia.eleven.core.pageable.Pagination;
import com.demcia.eleven.core.pageable.PaginationResult;
import com.demcia.eleven.upms.core.action.UserCreateAction;
import com.demcia.eleven.upms.core.action.UserQueryAction;
import com.demcia.eleven.upms.core.action.UserUpdateAction;
import com.demcia.eleven.upms.core.events.UserCreatedEvent;
import com.demcia.eleven.upms.core.events.UserUpdatedEvent;
import com.demcia.eleven.upms.domain.entity.User;
import com.demcia.eleven.upms.domain.entity.UserRepository;
import com.demcia.eleven.upms.domain.service.UserService;
import com.github.wenhao.jpa.Specifications;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public User createUser(UserCreateAction action) {
        userRepository.findByLogin(action.getLogin()).ifPresent(user -> {
            throw new ValidateFailureException("用户名已存在");
        });
        var user = new User();
        user.setLogin(action.getLogin());
        userRepository.save(user);
        eventPublisher.publishEvent(new UserCreatedEvent());
        return user;
    }

    @Override
    public Optional<User> getUser(String id) {
        return userRepository.findById(id);
    }

    @Override
    public void updateUser(User user, UserUpdateAction action) {
        if (StringUtils.isNotBlank(action.getNickname())) {
            user.setNickname(action.getNickname());
        }
        userRepository.save(user);
        eventPublisher.publishEvent(new UserUpdatedEvent());
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public PaginationResult<User> queryUser(UserQueryAction queryAction, Pagination pagination) {
        var spec = Specifications.<User>and()
                .eq(StringUtils.isNotBlank(queryAction.getLogin()), User.Fields.login, StringUtils.trim(queryAction.getLogin()))
                .eq(StringUtils.isNotBlank(queryAction.getType()), User.Fields.type, StringUtils.trim(queryAction.getType()))
                .eq(Objects.nonNull(queryAction.getState()), User.Fields.state, queryAction.getState())
                .build();
        var sort = Sort.by(User.Fields.id).descending();
        var pageable = PageableQueryHelper.toSpringDataPageable(pagination, sort);
        var page = userRepository.findAll(spec, pageable);
        return PageableQueryHelper.toPageResult(page);
    }
}
