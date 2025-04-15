package com.eleven.travel.domain.user;

import com.eleven.framework.domain.DataFoundException;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {

    Optional<User> findByUsername(String username);

    default User requireById(String id) {
        return findById(id).orElseThrow(DataFoundException::new);
    }

}
