package com.eleven.upms.application.service;

import com.eleven.core.data.Audition;
import com.eleven.core.data.QuerySupport;
import com.eleven.core.domain.DomainHelper;
import com.eleven.core.application.query.PageResult;
import com.eleven.core.time.TimeHelper;
import com.eleven.upms.api.application.command.UserCreateCommand;
import com.eleven.upms.api.application.command.UserQueryCommand;
import com.eleven.upms.api.application.command.UserStatusChangeCommand;
import com.eleven.upms.api.application.model.UserDetail;
import com.eleven.upms.api.domain.model.UserStatus;
import com.eleven.upms.application.convert.UserConvertor;
import com.eleven.upms.domain.manager.AuthorityManager;
import com.eleven.upms.domain.manager.UserManager;
import com.eleven.upms.domain.model.User;
import com.eleven.upms.domain.model.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    public static final String CACHE_NAME_USER = "user";

    private final UserManager userManager;
    private final UserConvertor userConvertor;
    private final UserRepository userRepository;
    private final AuthorityManager authorityManager;

    private final QuerySupport querySupport;

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public PageResult<UserDetail> queryUser(UserQueryCommand command) {
        var criteria = Criteria.empty();
        if (Objects.nonNull(command.getState())) {
            criteria = criteria.and(Criteria.where(User.Fields.status).is(command.getState()));
        }
        if (StringUtils.isNotBlank(command.getType())) {
            criteria = criteria.and(Criteria.where(User.Fields.type).is(StringUtils.trim(command.getType())));
        }
        if (StringUtils.isNotBlank(command.getUsername())) {
            criteria = criteria.and(Criteria.where(User.Fields.username).like(StringUtils.trim(command.getUsername()) + "%"));
        }
        if (BooleanUtils.isTrue(command.getIsLocked())) {
            criteria = criteria.and(Criteria.where(User.Fields.isLocked).is(true));
        }

        // not deleted yet
        criteria = criteria.and(Criteria.where(User.Fields.deleteAt).isNull());

        if (Objects.nonNull(command.getRanges())) {
            var ranges = Criteria.empty();
            for (UserQueryCommand.Range range : command.getRanges()) {
                switch (range) {
                    case locked -> ranges = ranges.or(Criteria.where(User.Fields.isLocked).is(true));
                    case unlocked -> ranges = ranges.or(Criteria.where(User.Fields.isLocked).is(false));
                }
            }
            criteria = criteria.and(ranges);
        }

        var sort=Sort.by(Audition.Fields.createAt).descending();
        var page = querySupport.query(User.class,criteria,command,sort);
        return page.map(userConvertor::toDetail);

    }

    @Cacheable(cacheNames = CACHE_NAME_USER, key = "#uid+'.detail'")
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Optional<UserDetail> getUser(String uid) {
        return userRepository.findById(uid)
            .filter(User::isEffective)
            .map(userConvertor::toDetail);
    }

    @CacheEvict(cacheNames = CACHE_NAME_USER, key = "#result.id+'.detail'")
    @Transactional(rollbackFor = Exception.class)
    public UserDetail createUser(UserCreateCommand command) {
        var user = User.builder()
            .id(DomainHelper.nextId())
            .username(command.getUsername())
            .type(User.USER_TYPE_ADMIN)
            .isLocked(false)
            .status(ObjectUtils.defaultIfNull(command.getState(), UserStatus.NORMAL))
            .registerAt(TimeHelper.localDateTime())
            .build();
        userManager.create(user);
        authorityManager.grant(user, command.getRoles());
        return userConvertor.toDetail(user);
    }

    @Cacheable(cacheNames = CACHE_NAME_USER, key = "#result.id+'.detail'")
    @Transactional(rollbackFor = Exception.class)
    public UserDetail updateUserStatus(String uid, UserStatusChangeCommand command) {
        var user = userRepository.requireById(uid);
        user.changeStatus(command.getStatus());
        userRepository.save(user);
        return userConvertor.toDetail(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(String uid) {
        var user = userRepository.requireById(uid);
        user.deleted();
        userRepository.save(user);
    }

}
