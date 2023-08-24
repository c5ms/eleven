package com.eleven.upms.domain;

import com.eleven.core.domain.AbstractAuditableDomain;
import com.eleven.core.domain.support.QueryTemplate;
import com.eleven.core.generate.IdentityGenerator;
import com.eleven.core.model.PaginationResult;
import com.eleven.upms.domain.configure.UpmsProperties;
import com.eleven.upms.dto.UserCreateAction;
import com.eleven.upms.dto.UserGrantAction;
import com.eleven.upms.dto.UserQuery;
import com.eleven.upms.dto.UserUpdateAction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserAuthorityRepository userAuthorityRepository;

    private final UpmsProperties upmsProperties;
    private final PasswordEncoder passwordEncoder;
    private final IdentityGenerator identityGenerator;
    private final QueryTemplate queryTemplate;

    public Optional<User> getUser(String id) {
        return userRepository.findById(id);
    }

    public Optional<User> readUser(String login, String password) {
        var userOptional = userRepository.findByUsername(login);
        if (userOptional.isEmpty()) {
            return userOptional;
        }
        var user = userOptional.get();
        var pass = passwordEncoder.matches(password, user.getPassword());
        if (pass) {
            return userOptional;
        }
        return Optional.empty();
    }

    public PaginationResult<User> queryUserPage(UserQuery filter) {
        var criteria = Criteria.empty();
        if (null != filter.getState()) {
            criteria = Criteria.where(User.Fields.state).is(filter.getState());
        }
        if (StringUtils.isNotBlank(filter.getType())) {
            criteria = Criteria.where(User.Fields.type).is(filter.getType());
        }
        if (StringUtils.isNotBlank(filter.getUsername())) {
            criteria = Criteria.where(User.Fields.username).like(filter.getUsername() + "%");
        }
        if (Objects.nonNull(filter.getIsLocked())) {
            criteria = Criteria.where(User.Fields.isLocked).is(filter.getIsLocked());
        }
        var query = Query
                .query(criteria)
                .sort(Sort.by(AbstractAuditableDomain.Fields.createAt).descending());
        return queryTemplate.findPage(query, User.class, filter);
    }

    public Collection<UserAuthority> getAuthorities(User user) {
        return userAuthorityRepository.findByUserId(user.getId());
    }

    public User createUser(UserCreateAction action) {
        var id = identityGenerator.next();
        var user = new User(id, action);
        validate(user);
        user.setPassword(passwordEncoder.encode(upmsProperties.getDefaultPassword()));
        return userRepository.save(user);
    }

    public void updateUser(User user, UserUpdateAction action) {
        user.update(action);
        validate(user);
        userRepository.save(user);
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
        userRepository.delete(user);
    }

    public void grant(User user, UserGrantAction action) {
        var authority = Authority.of(action.getType(), action.getName());
        userAuthorityRepository.deleteByUserAndAuthority(user.getId(), authority.type(), authority.name());
        var id = identityGenerator.next();
        var userAuthority = new UserAuthority(id, user, authority);
        userAuthorityRepository.save(userAuthority);
    }

    /**
     * 验证用户信息
     *
     * @param user 用户
     */
    private void validate(User user) throws UserException {
        // 验证，用户名不能重复
        var existUser = userRepository.findByUsername(user.getUsername())
                .filter(check -> !StringUtils.equals(check.getId(), user.getId()));
        if (existUser.isPresent()) {
            throw UserError.USER_NAME_REPEAT.exception();
        }
    }


}
