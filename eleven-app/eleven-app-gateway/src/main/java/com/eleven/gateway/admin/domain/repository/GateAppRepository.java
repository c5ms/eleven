package com.eleven.gateway.admin.domain.repository;

import com.eleven.gateway.admin.domain.entity.GateApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * @author yz
 */
public interface GateAppRepository extends JpaRepository<GateApp, String>, JpaSpecificationExecutor<GateApp> {

    /**
     * 根据应用标识查询应用
     *
     * @param appId 应用唯一标识
     * @return 应用
     */
    Optional<GateApp> findByAppId(String appId);

    @Modifying
    @Query("update GateApp e set e.statRequestCount= COALESCE(e.statRequestCount,0) + ?2 where e.appId=?1")
    void increaseRequestCount(String id, Integer count);

}
