package com.eleven.hotel.domain.model.hotel;

import com.eleven.core.data.NoEntityException;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RegisterRepository extends CrudRepository<Register, String> {

    Optional<Register> findByRegisterId(String registerId);

    default Register require(String registerId)  {
        return findByRegisterId(registerId).orElseThrow(NoEntityException::new);
    }
}
