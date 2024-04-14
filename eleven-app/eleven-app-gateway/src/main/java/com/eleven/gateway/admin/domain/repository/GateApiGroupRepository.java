package com.eleven.gateway.admin.domain.repository;

import com.eleven.gateway.admin.domain.entity.GateApiGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface GateApiGroupRepository extends JpaRepository<GateApiGroup, String>, JpaSpecificationExecutor<GateApiGroup> {


    @Modifying
    @Query("update GateApiGroup e set e.statRequestCount= COALESCE(e.statRequestCount,0) + ?2 where e.id=?1")
    void increaseRequestCount(String id, Integer count);

}
