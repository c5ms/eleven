package com.eleven.upms.domain;

import com.eleven.core.domain.AbstractAuditableDomain;
import com.eleven.core.domain.DomainSupport;
import com.eleven.core.exception.ElevenRuntimeException;
import com.eleven.core.model.PaginationResult;
import com.eleven.upms.core.UpmsConstants;
import com.eleven.upms.model.UserCreateAction;
import com.eleven.upms.model.UserFilter;
import com.eleven.upms.model.UserUpdateAction;
import com.eleven.upms.configure.UpmsProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthorityManager authorityManager;
    private final DomainSupport domainSupport;
    private final UpmsProperties upmsProperties;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> getUser(String id) {
        return userRepository.findById(id);
    }

    public Optional<User> loginUser(String login, String password) {
        var userOptional = userRepository.findByUsername(login);
        if (userOptional.isEmpty()) {
            return userOptional;
        }
        var user = userOptional.get();
        var pass = passwordEncoder.matches(password, user.getPassword());
        if (!pass) {
            return Optional.empty();
        }
        user.login();
        userRepository.save(user);
        return userOptional;
    }

    public PaginationResult<User> queryUserPage(UserFilter filter) {
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

        if (Objects.nonNull(filter.getRanges())) {
            var ranges = Criteria.empty();
            for (UserFilter.Range range : filter.getRanges()) {
                switch (range) {
                    case locked -> ranges = ranges.or(Criteria.where(User.Fields.isLocked).is(true));
                    case unlocked -> ranges = ranges.or(Criteria.where(User.Fields.isLocked).is(false));
                }
            }
            criteria = criteria.and(ranges);
        }

        var query = Query
                .query(criteria)
                .sort(Sort.by(AbstractAuditableDomain.Fields.createAt).descending());
        return domainSupport.queryForPage(query, User.class, filter.getPage(), filter.getSize());
    }


    @Transactional(rollbackFor = Exception.class)
    public User createUser(UserCreateAction action) {
        var id = domainSupport.nextId();
        var user = new User(id, action);
        validate(user);
        user.setPassword(passwordEncoder.encode(upmsProperties.getDefaultPassword()));
        userRepository.save(user);
        grantRoles(user, action.getRoles());
        return user;
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateUser(User user, UserUpdateAction action) {
        user.update(action);
        validate(user);
        userRepository.save(user);
        grantRoles(user, action.getRoles());
    }

    public void lockUser(User user) {
        user.lock();
        userRepository.save(user);
    }

    public void unlockUser(User user) {
        user.unlock();
        userRepository.save(user);
    }

    public void deleteUser(User user) {
        user.delete();
        userRepository.delete(user);
    }

    private void validate(User user) throws ElevenRuntimeException {
        // 验证，用户名不能重复
        var existUser = userRepository.findByUsername(user.getUsername())
                .filter(check -> !StringUtils.equals(check.getId(), user.getId()));
        if (existUser.isPresent()) {
            throw UpmsConstants.ERROR_USER_NAME_REPEAT.exception();
        }
    }

    private void grantRoles(User user, List<String> roles) {
        var owner = Authority.ownerOfUser(user.getId());
        var powers = roles.stream().map(Authority::powerOfRole).toList();

        authorityManager.revoke(owner, Authority.POWER_ROLE);
        authorityManager.grant(owner, powers);
    }

    public List<Authority.Power> listPower(User user) {
        var owner = Authority.ownerOfUser(user.getId());
        return authorityManager.powerOf(owner);
    }
}
