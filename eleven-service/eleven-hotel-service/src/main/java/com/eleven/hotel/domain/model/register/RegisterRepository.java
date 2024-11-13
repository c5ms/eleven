package com.eleven.hotel.domain.model.register;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RegisterRepository extends JpaRepository<Register, Long>, JpaSpecificationExecutor<Register> {

}
