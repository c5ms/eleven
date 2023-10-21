package com.eleven.upms.domain;

import cn.hutool.extra.spring.SpringUtil;
import com.eleven.core.domain.AbstractAuditDomain;
import com.eleven.core.domain.AbstractDeletableDomain;
import com.eleven.core.domain.DomainSupport;
import com.eleven.core.domain.PaginationResult;
import com.eleven.core.exception.ProcessRuntimeException;
import com.eleven.core.security.Principal;
import com.eleven.upms.core.UpmsConstants;
import com.eleven.upms.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserConvertor userConvertor;
    private final PasswordService passwordService;
    private final UserRepository userRepository;
    private final AuthorityManager authorityManager;
    private final DomainSupport domainSupport;

    @Transactional(readOnly = true)
    public PaginationResult<UserDto> queryUserPage(UserQuery filter) {

        var criteria = Criteria.empty();
        if (Objects.nonNull(filter.getState())) {
            criteria = criteria.and(Criteria.where(User.Fields.state).is(filter.getState()));
        }
        if (StringUtils.isNotBlank(filter.getType())) {
            criteria = criteria.and(Criteria.where(User.Fields.type).is(StringUtils.trim(filter.getType())));
        }
        if (StringUtils.isNotBlank(filter.getUsername())) {
            criteria = criteria.and(Criteria.where(User.Fields.username).like(StringUtils.trim(filter.getUsername()) + "%"));
        }
        if (BooleanUtils.isTrue(filter.getIsLocked())) {
            criteria = criteria.and(Criteria.where(User.Fields.isLocked).is(true));
        }

        // not deleted yet
        criteria = criteria.and(Criteria.where(AbstractDeletableDomain.Fields.deleteAt).isNull());

        if (Objects.nonNull(filter.getRanges())) {
            var ranges = Criteria.empty();
            for (UserQuery.Range range : filter.getRanges()) {
                switch (range) {
                    case locked -> ranges = ranges.or(Criteria.where(User.Fields.isLocked).is(true));
                    case unlocked -> ranges = ranges.or(Criteria.where(User.Fields.isLocked).is(false));
                }
            }
            criteria = criteria.and(ranges);
        }
        var query = Query.query(criteria).sort(Sort.by(AbstractAuditDomain.Fields.createAt).descending());
        var page = domainSupport.queryPage(query, User.class, filter.getPage(), filter.getSize());
        return page.map(userConvertor::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<UserDto> getUser(String uid) {
        var user = userRepository.findById(uid);
        // 1.  only return effective ( not deleted ) user
        return user.filter(AbstractDeletableDomain::isEffective)
                .map(userConvertor::toDto);
    }

    @Cacheable(cacheNames = UpmsConstants.CACHE_NAME_USER, key = "#uid+'.summary'")
    @Transactional(readOnly = true)
    public Optional<UserSummary> getUserSummary(String uid) {
        return userRepository.findSummaryById(uid);
    }

    @CacheEvict(cacheNames = UpmsConstants.CACHE_NAME_USER, key = "#result.id+'.summary'")
    @Transactional(rollbackFor = Exception.class)
    public UserDto createUser(UserCreateAction action) {
        var id = domainSupport.getNextId();
        var user = new User(id, action);
        validate(user);
        user.setPassword(passwordService.defaultPassword());
        grant(user, action.getRoles());
        userRepository.save(user);
        SpringUtil.publishEvent(new UserCreatedEvent(id));
        return userConvertor.toDto(user);
    }

    @CacheEvict(cacheNames = UpmsConstants.CACHE_NAME_USER, key = "#uid+'.summary'")
    @Transactional(rollbackFor = Exception.class)
    public UserDto updateUser(String uid, UserUpdateAction action) {
        var user = userRepository.requireById(uid);
        user.update(action);
        validate(user);
        grant(user, action.getRoles());
        userRepository.save(user);
        SpringUtil.publishEvent(new UserUpdatedEvent(uid));
        return userConvertor.toDto(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(String uid) {
        var user = userRepository.requireById(uid);
        SpringUtil.publishEvent(new UserDeletedEvent(uid));
        user.delete();
        userRepository.save(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public Principal loginUser(String login, String password) {
        var userOptional = userRepository.findByUsername(login);
        if (userOptional.isEmpty()) {
            throw UpmsConstants.ERROR_LOGIN_PASSWORD.exception();
        }
        var user = userOptional.get();
        var pass = passwordService.valid(password, user.getPassword());
        if (!pass) {
            throw UpmsConstants.ERROR_LOGIN_PASSWORD.exception();
        }
        if (user.isDeleted()) {
            throw UpmsConstants.ERROR_USER_ALREADY_DELETED.exception();
        }
        user.login();
        userRepository.save(user);
        SpringUtil.publishEvent(new userLoginEvent(user.getId()));
        return userOptional.get().toPrincipal();
    }

    private void validate(User user) throws ProcessRuntimeException {
        // 验证，用户名不能重复
        var existUser = userRepository.findByUsername(user.getUsername()).filter(check -> !StringUtils.equals(check.getId(), user.getId()));
        if (existUser.isPresent()) {
            throw UpmsConstants.ERROR_USER_NAME_REPEAT.exception();
        }
    }

    private void grant(User user, List<String> roles) {
        var owner = Authority.ownerOf(user.toPrincipal());
        var powers = roles.stream().map(Authority::powerOfRole).toList();

        authorityManager.revoke(owner, Authority.POWER_ROLE);
        authorityManager.grant(owner, powers);
    }


}
