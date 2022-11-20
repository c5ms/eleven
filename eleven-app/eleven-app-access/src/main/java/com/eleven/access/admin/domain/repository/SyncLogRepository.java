package com.eleven.access.admin.domain.repository;

import com.cnetong.access.admin.domain.entity.SyncLog;
import com.cnetong.common.domain.DomainRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface SyncLogRepository extends DomainRepository<SyncLog, String>, JpaSpecificationExecutor<SyncLog> {
    @Modifying
    @Query("delete from SysTaskLog e where e.startTime < :limit")
    void deleteDaysAge(@Param("limit") LocalDateTime limit);

    @Query("select  count(e.id) from SysTaskLog e where e.success=:success and  e.startTime > :beginTime")
    long countBySuccess(@Param("success") boolean success, @Param("beginTime") LocalDateTime beginTime);

}
