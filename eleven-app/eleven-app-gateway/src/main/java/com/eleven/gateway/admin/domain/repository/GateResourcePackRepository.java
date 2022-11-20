package com.eleven.gateway.admin.domain.repository;

import com.eleven.gateway.admin.domain.entity.GateResourcePack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface GateResourcePackRepository extends JpaRepository<GateResourcePack, String> {
    @Query("select g from GateResourcePack g where g.resourceId = ?1 order by g.createDate desc")
    Collection<GateResourcePack> findByResourceId(String resourceId);


    @Modifying(flushAutomatically = true)
    @Query("update GateResourcePack set isReleased=false where resourceId=:resourceId and id <> :expectId")
    void updateLatest(@Param("resourceId") String resourceId, @Param("expectId") String expectId);


}
