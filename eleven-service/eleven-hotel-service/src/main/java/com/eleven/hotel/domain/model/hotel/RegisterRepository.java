package com.eleven.hotel.domain.model.hotel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RegisterRepository extends JpaRepository<Register, Integer>, JpaSpecificationExecutor<Register> {

}
