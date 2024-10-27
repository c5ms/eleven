package com.eleven.hotel.domain.model.hotel;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RegisterRepository extends CrudRepository<Register, String> {

    Optional<Register> findByRegisterId(String registerId);

    default Register require(String registerId) throws RegisterNotFoundException {
        return findByRegisterId(registerId).orElseThrow(() -> new RegisterNotFoundException(registerId));
    }
}
