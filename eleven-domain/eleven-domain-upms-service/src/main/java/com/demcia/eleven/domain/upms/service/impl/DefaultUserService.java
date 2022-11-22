package com.demcia.eleven.domain.upms.service.impl;

import com.demcia.eleven.core.helper.PageableQueryHelper;
import com.demcia.eleven.core.pageable.PageResult;
import com.demcia.eleven.domain.upms.action.UserQueryAction;
import com.demcia.eleven.domain.upms.action.UserUpdateAction;
import com.demcia.eleven.domain.upms.entity.User;
import com.demcia.eleven.domain.upms.entity.UserRepository;
import com.demcia.eleven.domain.upms.service.UserService;
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
    public void saveUser(User user) {
        userRepository.save(user);
    }


    @Override
    public Optional<User> getUser(String id) {
        return userRepository.findById(id);
    }

    @Override
    public void updateUser(User user, UserUpdateAction action) {
        user.update(action);
        userRepository.save(user);
    }

    @Override
    public PageResult<User> queryUser(UserQueryAction queryAction) {
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
