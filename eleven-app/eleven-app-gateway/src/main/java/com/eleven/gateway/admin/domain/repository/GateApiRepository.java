package com.eleven.gateway.admin.domain.repository;

import com.eleven.gateway.admin.domain.entity.GateApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GateApiRepository extends JpaRepository<GateApi, String>, JpaSpecificationExecutor<GateApi> {

    @Query("from GateApi where published=:published order by order asc")
    List<GateApi> findPublishedApis(@Param("published") boolean published);

    List<GateApi> deleteByGroup(@Param("group") String group);

    List<GateApi> findAllByGroup(@Param("group") String group);

    Integer countByGroupAndPublished(@Param("group") String group,@Param("published") boolean published);

    @Query("select coalesce(sum(coalesce(e.statRequestCount,0) ),0) from GateApi e ")
    Long sumRequestCount();

    @Modifying
    @Query("update GateApi e set e.statRequestCount= COALESCE(e.statRequestCount,0) + ?2 where e.id=?1")
    void increaseRequestCount( String id,Integer count);

}
