package com.demcia.eleven.upms.domain;

import com.demcia.eleven.core.exception.DataNotFoundException;
import com.demcia.eleven.core.exception.ProcessFailureException;
import com.demcia.eleven.core.pageable.PaginationResult;
import com.demcia.eleven.domain.identity.IdGenerator;
import com.demcia.eleven.upms.domain.action.UserCreateAction;
import com.demcia.eleven.upms.domain.action.UserGrantAction;
import com.demcia.eleven.upms.domain.action.UserQueryAction;
import com.demcia.eleven.upms.domain.action.UserUpdateAction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final IdGenerator idGenerator;
    private final List<UserGrantor> userGrantors;

    public User requireUser(String id) {
        return getUser(id).orElseThrow(() -> DataNotFoundException.of("用户不存在"));
    }

    public User createUser(UserCreateAction action) {
        action.setLogin(UUID.randomUUID().toString());
        var user = new User(idGenerator.nextId(), action);
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

    public PaginationResult<User> queryUser(UserQueryAction queryAction) {
        return null;
    }

    public AuthoritySet authoritiesOfUser(User user) {
        var authorities = new AuthoritySet();
        var roles = userRepository.findUserRole(user.getId());
        authorities.addRoles(roles);
        return authorities;
    }

    public void grant(User user, UserGrantAction action) {
        var authority = Authority.of(action.getType(), action.getName());
        var granters = userGrantors.stream()
                .filter(userGrantor -> userGrantor.support(authority))
                .collect(Collectors.toSet());
        if (granters.isEmpty()) {
              throw ProcessFailureException.of("不支持的授权类型");
        }
        for (UserGrantor granter : granters) {
            granter.grant(user,authority);
        }
    }
}
