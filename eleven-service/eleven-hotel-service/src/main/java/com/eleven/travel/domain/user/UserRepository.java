package com.eleven.travel.domain.user;

import com.eleven.framework.data.DataFoundException;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {

    Optional<User> findByUsername(String username);

    default User requireById(String id) {
        return findById(id).orElseThrow(DataFoundException::new);
    }

}
