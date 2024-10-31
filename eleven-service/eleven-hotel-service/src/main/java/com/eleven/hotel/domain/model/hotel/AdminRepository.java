package com.eleven.hotel.domain.model.hotel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface AdminRepository   extends JpaRepository<Admin, Integer>, JpaSpecificationExecutor<Admin> {
}
