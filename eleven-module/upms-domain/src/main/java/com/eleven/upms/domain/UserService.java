package com.eleven.upms.domain;

import com.eleven.core.generate.IdentityGenerator;
import com.eleven.core.query.Pagination;
import com.eleven.core.query.QueryResult;
import com.eleven.upms.domain.action.UserCreateAction;
import com.eleven.upms.domain.action.UserUpdateAction;
import com.eleven.upms.domain.configure.UpmsProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
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
    private final JdbcAggregateTemplate jdbcAggregateTemplate;

    /**
     * 读取指定 ID 的用户
     *
     * @param id 用户 ID
     * @return 用户
     */
    public Optional<User> getUser(String id) {
        return userRepository.findById(id);
    }

    /**
     * 验证用户
     *
     * @param login    登入账号
     * @param password 验证口令
     * @return 如果可以找到指定登入账号的用户，并且密码相符，则返回该用户，否则返回空。
     */
    public Optional<User> readUser(String login, String password) {
        var userOptional = userRepository.findByLogin(login);
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

    public QueryResult<User> queryUser(UserFilter filter, Pagination pagination) {
        var criteria = Criteria.empty();
        if (StringUtils.isNotBlank(filter.getState())) {
            criteria = criteria.and(Criteria.where("state").is(filter.getState()));
        }
        if (StringUtils.isNotBlank(filter.getLogin())) {
            criteria = criteria.and(Criteria.where("login").like(filter.getLogin() + "%"));
        }
        var query = Query.query(criteria);
        var pageable = Pageable.ofSize(pagination.getSize()).withPage(pagination.getPage());
        var users = jdbcAggregateTemplate.findAll(query, User.class, pageable);
        return QueryResult.of(users.getContent(), users.getTotalElements()).withPagination(pagination);
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

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public void grant(User user, Authority authority) {
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
        var existUser = userRepository.findByLogin(user.getLogin())
                .filter(check -> !StringUtils.equals(check.getId(), user.getId()));
        if (existUser.isPresent()) {
            throw UserError.USER_NAME_REPEAT.exception();
        }
    }

}
