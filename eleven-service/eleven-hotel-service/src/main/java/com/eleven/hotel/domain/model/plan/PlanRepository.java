package com.eleven.hotel.domain.model.plan;

import io.hypersistence.utils.spring.repository.BaseJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PlanRepository extends BaseJpaRepository<Plan, Long>, JpaSpecificationExecutor<Plan> {

    @Query("from Plan where hotelId=:#{#key.hotelId} and planId=:#{#key.planId} ")
    Optional<Plan> findByKey(@Param("key") PlanKey key);

}
