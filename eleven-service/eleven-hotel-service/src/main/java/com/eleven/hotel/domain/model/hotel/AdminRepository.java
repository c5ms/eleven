package com.eleven.hotel.domain.model.hotel;

import io.hypersistence.utils.spring.repository.BaseJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AdminRepository extends BaseJpaRepository<Admin, Long>, JpaSpecificationExecutor<Admin> {
}
