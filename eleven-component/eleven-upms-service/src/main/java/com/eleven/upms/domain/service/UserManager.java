package com.eleven.upms.domain.service;

import com.eleven.core.data.AbstractAuditEntity;
import com.eleven.core.data.AbstractDeletableEntity;
import com.eleven.core.data.DomainSupport;
import com.eleven.core.domain.PaginationResult;
import com.eleven.core.event.EventSupport;
import com.eleven.core.exception.ProcessFailureException;
import com.eleven.core.security.Principal;
import com.eleven.upms.core.UpmsConstants;
import com.eleven.upms.core.command.UserCreateCommand;
import com.eleven.upms.core.command.UserQueryCommand;
import com.eleven.upms.core.command.UserUpdateCommand;
import com.eleven.upms.core.event.UserCreatedEvent;
import com.eleven.upms.core.event.UserDeletedEvent;
import com.eleven.upms.core.event.UserLoginEvent;
import com.eleven.upms.core.event.UserUpdatedEvent;
import com.eleven.upms.core.model.UserDetail;
import com.eleven.upms.domain.convert.UserConvertor;
import com.eleven.upms.domain.model.Authority;
import com.eleven.upms.domain.model.User;
import com.eleven.upms.domain.model.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
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
public class UserManager {

    private final EventSupport eventSupport;
    private final DomainSupport domainSupport;

    private final PasswordManager passwordManager;
    private final AuthorityManager authorityManager;

    private final UserConvertor userConvertor;
    private final UserRepository userRepository;

    public PaginationResult<UserDetail> query(UserQueryCommand command) {
        var criteria = Criteria.empty();
        if (Objects.nonNull(command.getState())) {
            criteria = criteria.and(Criteria.where(User.Fields.state).is(command.getState()));
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
        criteria = criteria.and(Criteria.where(AbstractDeletableEntity.Fields.deleteAt).isNull());

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

        var query = Query.query(criteria).sort(Sort.by(AbstractAuditEntity.Fields.createAt).descending());
        var page = domainSupport.queryPage(query, User.class, command.getPage(), command.getSize());
        return page.map(userConvertor::toDto);
    }

    public Optional<UserDetail> get(String uid) {
        var user = userRepository.findById(uid);
        // 1.  only return effective ( not deleted ) user
        return user.filter(AbstractDeletableEntity::isEffective)
            .map(userConvertor::toDto);
    }

    public UserDetail create(UserCreateCommand action) {
        var id = domainSupport.getNextId();
        var user = new User(id, action);
        validate(user);
        user.changePassword(passwordManager.defaultPassword());
        grant(user, action.getRoles());
        userRepository.save(user);
        eventSupport.publishInternalEvent(new UserCreatedEvent(id));
        return userConvertor.toDto(user);
    }

    public UserDetail update(String uid, UserUpdateCommand action) {
        var user = userRepository.requireById(uid);
        user.update(action);
        validate(user);
        grant(user, action.getRoles());
        userRepository.save(user);
        eventSupport.publishInternalEvent(new UserUpdatedEvent(user.getId()));
        return userConvertor.toDto(user);
    }

    public void delete(String uid) {
        var user = userRepository.requireById(uid);
        eventSupport.publishInternalEvent(new UserDeletedEvent(uid));
        user.delete();
        userRepository.save(user);
    }

    public Principal login(String login, String password) {
        var userOptional = userRepository.findByUsername(login);
        if (userOptional.isEmpty()) {
            throw UpmsConstants.ERROR_LOGIN_PASSWORD.exception();
        }
        var user = userOptional.get();
        var pass = passwordManager.valid(password, user.getPassword());
        if (!pass) {
            throw UpmsConstants.ERROR_LOGIN_PASSWORD.exception();
        }
        if (user.isDeleted()) {
            throw UpmsConstants.ERROR_USER_ALREADY_DELETED.exception();
        }
        user.login();
        userRepository.save(user);
        eventSupport.publishInternalEvent(new UserLoginEvent(user.getId()));
        return userOptional.get().toPrincipal();
    }

    private void validate(User user) throws ProcessFailureException {
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
