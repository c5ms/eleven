package com.demcia.eleven.upms.domain.service.impl;

import com.demcia.eleven.core.domain.helper.PageableQueryHelper;
import com.demcia.eleven.core.exception.ProcessFailureException;
import com.demcia.eleven.core.pageable.PaginationResult;
import com.demcia.eleven.upms.core.action.UserCreateAction;
import com.demcia.eleven.upms.core.action.UserQueryAction;
import com.demcia.eleven.upms.core.action.UserUpdateAction;
import com.demcia.eleven.upms.core.code.UserErrors;
import com.demcia.eleven.upms.core.event.UserCreatedEvent;
import com.demcia.eleven.upms.core.event.UserUpdatedEvent;
import com.demcia.eleven.upms.domain.entity.User;
import com.demcia.eleven.upms.domain.entity.UserRepository;
import com.demcia.eleven.upms.domain.service.UserService;
import com.github.wenhao.jpa.Specifications;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
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

    private final MapperFacade mapperFacade;

    @Override
    public User createUser(UserCreateAction action) {
        // 验证，用户名不能重复
        userRepository.findByLogin(action.getLogin()).ifPresent(user -> {
            throw ProcessFailureException.of(UserErrors.USER_NAME_REPEAT);
        });
        var user = mapperFacade.map(action, User.class);
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
        if (Objects.nonNull(action.getNickname())) {
            user.setNickname(StringUtils.trim(action.getNickname()));
        }

        userRepository.save(user);
        eventPublisher.publishEvent(new UserUpdatedEvent());
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public PaginationResult<User> queryUser(UserQueryAction queryAction) {
        var spec = Specifications.<User>and()
                .eq(StringUtils.isNotBlank(queryAction.getLogin()), User.Fields.login, StringUtils.trim(queryAction.getLogin()))
                .eq(StringUtils.isNotBlank(queryAction.getType()), User.Fields.type, StringUtils.trim(queryAction.getType()))
                .eq(Objects.nonNull(queryAction.getState()), User.Fields.state, queryAction.getState())
                .build();
        var sort = Sort.by(User.Fields.id).descending();
        var pageable = PageableQueryHelper.toSpringDataPageable(queryAction, sort);
        var page = userRepository.findAll(spec, pageable);
        return PageableQueryHelper.toPageResult(page);
    }
}
