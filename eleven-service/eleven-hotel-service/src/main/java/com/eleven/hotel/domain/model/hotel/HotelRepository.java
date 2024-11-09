package com.eleven.hotel.domain.model.hotel;

import io.hypersistence.utils.spring.repository.BaseJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HotelRepository extends BaseJpaRepository<Hotel, Long>, JpaSpecificationExecutor<Hotel> {

}
