package com.motiveschina.hotel.features.user;


import java.util.Optional;
import com.eleven.framework.domain.DataFoundException;
import org.springframework.data.repository.ListCrudRepository;

public interface RoleRepository extends ListCrudRepository<Role, String> {
    Optional<Role> findByCode(String code);


    default Role requireById(String id) {
        return findById(id).orElseThrow(DataFoundException::new);
    }
}
