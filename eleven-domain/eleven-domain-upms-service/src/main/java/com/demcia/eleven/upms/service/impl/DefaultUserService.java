package com.demcia.eleven.upms.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.demcia.eleven.core.helper.PageableQueryHelper;
import com.demcia.eleven.core.pageable.PageResult;
import com.demcia.eleven.upms.core.action.UserCreateAction;
import com.demcia.eleven.upms.core.action.UserUpdateAction;
import com.demcia.eleven.upms.domain.User;
import com.demcia.eleven.upms.domain.UserRepository;
import com.demcia.eleven.upms.core.events.UserCreatedEvent;
import com.demcia.eleven.upms.core.events.UserUpdatedEvent;
import com.demcia.eleven.upms.core.action.UserQuery;
import com.demcia.eleven.upms.service.UserService;
import com.github.wenhao.jpa.Specifications;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {
    private final UserRepository userRepository;

    @Override
    public User createUser(UserCreateAction action) {
        var user = new User();
        user.setUsername(action.getUsername());
        userRepository.save(user);
        SpringUtil.publishEvent(new UserCreatedEvent());
        return user;
    }

    @Override
    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void updateUser(User user, UserUpdateAction action) {
        if (StringUtils.isNotBlank(action.getNickname())) {
            user.setNickname(action.getNickname());
        }
        userRepository.save(user);
        SpringUtil.publishEvent(new UserUpdatedEvent());
    }

    @Override
    public PageResult<User> queryUser(UserQuery queryAction) {
        var spec = Specifications.<User>and()
                .like(StringUtils.isNotBlank(queryAction.getUsername()), User.Fields.username, "%" + StringUtils.trim(queryAction.getUsername()) + "%")
                .eq(StringUtils.isNotBlank(queryAction.getType()), User.Fields.type, StringUtils.trim(queryAction.getType()))
                .eq(Objects.nonNull(queryAction.getState()), User.Fields.state, queryAction.getState())
                .build();
        var sort = Sort.by(User.Fields.id).descending();
        var pageable = PageableQueryHelper.toSpringDataPageable(queryAction, sort);
        var page = userRepository.findAll(spec, pageable);
        return PageableQueryHelper.toPageResult(page);
    }
}
