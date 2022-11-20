package com.eleven.access.admin.domain.repository;

import com.cnetong.access.admin.domain.entity.SyncTask;
import com.cnetong.common.domain.DomainRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface SyncTaskRepository extends DomainRepository<SyncTask, String> {

    long countByStarted(boolean started);

    @Modifying
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Query("update SyncTask t set t.running=:running ,t.currentCompleted=:completed where t.id=:id")
    void updateRunning(@Param("id") String id, @Param("running") Boolean running, @Param("completed") Long completed);

    @Modifying
    @Query("delete from SyncTaskMapping t where t.taskId is null")
    void clearMapping();
}
