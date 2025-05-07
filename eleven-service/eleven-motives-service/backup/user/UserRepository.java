package com.motiveschina.hotel.features.user;

import java.util.Optional;
import com.eleven.framework.domain.DataFoundException;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {

    Optional<User> findByUsername(String username);

    default User requireById(String id) {
        return findById(id).orElseThrow(DataFoundException::new);
    }

}
