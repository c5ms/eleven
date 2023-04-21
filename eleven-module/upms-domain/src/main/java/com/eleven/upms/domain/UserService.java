package com.eleven.upms.domain;

import com.eleven.core.generate.IdentityGenerator;
import com.eleven.core.message.MessageSender;
import com.eleven.core.query.Pagination;
import com.eleven.core.query.QueryResult;
import com.eleven.upms.domain.action.UserCreateAction;
import com.eleven.upms.domain.action.UserUpdateAction;
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

    private final PasswordEncoder passwordEncoder;
    private final IdentityGenerator identityGenerator;
    private final JdbcAggregateTemplate jdbcAggregateTemplate;

    private final UserValidator userValidator;
    private final UserRepository userRepository;
    private final UserAuthorityRepository userAuthorityRepository;

    public User createUser(UserCreateAction action) {
        var id = identityGenerator.next();
        var user = new User(id, action);
        user.setPassword(passwordEncoder.encode("123456"));
        userValidator.validate(user);
        return userRepository.save(user);
    }


    public Optional<User> getUser(String id) {
        return userRepository.findById(id);
    }

    public Optional<User> readUser(String login) {
        return userRepository.findByLogin(login);
    }

    public void updateUser(User user, UserUpdateAction action) {
        user.update(action);
        userValidator.validate(user);
        userRepository.save(user);
    }

    /**
     * 验证用户
     *
     * @param login    登入账号
     * @param password 验证口令
     * @return 如果可以找到指定登入账号的用户，并且密码相符，则返回该用户，否则返回空。
     */
    public Optional<User> readUser(String login, String password) {
        var userOptional = readUser(login);
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

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public QueryResult<User> queryUser(UserFilter filter, Pagination pagination) {
        var criteria = Criteria.empty();
        if (StringUtils.isNotBlank(filter.getState())) {
            criteria = criteria.and(Criteria.where("state_").is(filter.getState()));
        }
        if (StringUtils.isNotBlank(filter.getLogin())) {
            criteria = criteria.and(Criteria.where("login_").like("%" + filter.getLogin()));
        }
        var query = Query.query(criteria);
        var pageable = Pageable.ofSize(pagination.getSize()).withPage(pagination.getPage());
        var users = jdbcAggregateTemplate.findAll(query, User.class, pageable);
        return QueryResult.of(users.getContent(), users.getTotalElements()).withPagination(pagination);
    }

    public Collection<UserAuthority> getAuthorities(User user) {
        return userAuthorityRepository.findByUserId(user.getId());
    }

    public void grant(User user, Authority authority) {
        userAuthorityRepository.deleteByUserAndAuthority(user.getId(), authority.type(), authority.name());
        var id = identityGenerator.next();
        var userAuthority = new UserAuthority(id, user, authority);
        userAuthorityRepository.save(userAuthority);
    }

}
