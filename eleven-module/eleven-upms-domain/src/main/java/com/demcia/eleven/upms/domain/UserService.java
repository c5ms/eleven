package com.demcia.eleven.upms.domain;

import com.demcia.eleven.core.generate.IdentityGenerator;
import com.demcia.eleven.core.query.Pagination;
import com.demcia.eleven.core.query.QueryResult;
import com.demcia.eleven.upms.domain.action.UserCreateAction;
import com.demcia.eleven.upms.domain.action.UserUpdateAction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final IdentityGenerator identityGenerator;
    private final JdbcAggregateTemplate jdbcAggregateTemplate;

    private final UserValidator userValidator;
    private final UserRepository userRepository;
    private final UserAuthorityRepository userAuthorityRepository;

    public User createUser(UserCreateAction action) {
        var id = identityGenerator.next();
        action.setLogin(UUID.randomUUID().toString());
        var user = new User(id, action);
        userValidator.validate(user);
        return userRepository.save(user);
    }

    public Optional<User> getUser(String id) {
        return userRepository.findById(id);
    }

    public void updateUser(User user, UserUpdateAction action) {
        user.update(action);
        userValidator.validate(user);
        userRepository.save(user);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }


    public QueryResult<User> queryUser(UserFilter filter, Pagination pagination) {
        var criteria = Criteria.empty();
        if (Objects.nonNull(filter.getState())) {
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
        return userAuthorityRepository.findByUser(user.getId());
    }

    public void grant(User user, Authority authority) {
        userAuthorityRepository.deleteByUserAndAuthority(user.getId(), authority.getType(), authority.getName());
        var id = identityGenerator.next();
        var userAuthority = new UserAuthority(id, user, authority);
        userAuthorityRepository.save(userAuthority);
    }

}
