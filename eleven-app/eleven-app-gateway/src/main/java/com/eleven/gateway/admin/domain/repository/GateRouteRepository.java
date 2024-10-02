package com.eleven.gateway.admin.domain.repository;

import com.eleven.gateway.admin.domain.entity.GateRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface GateRouteRepository extends JpaRepository<GateRoute, String>, JpaSpecificationExecutor<GateRoute> {

    @Query("from GateRoute where published=:published and stackId is null order by order asc")
    List<GateRoute> findGlobalRoutes(@Param("published") boolean published);

    Collection<GateRoute> findByStackIdOrderByOrderAsc(String id);
}
