package com.motiveschina.hotel.features.plan;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlanRepository extends JpaRepository<Plan, Long>, JpaSpecificationExecutor<Plan> {

    @Query("from Plan where hotelId=:#{#key.hotelId} and planId=:#{#key.planId} ")
    Optional<Plan> findByKey(@Param("key") PlanKey key);

}
