package com.eleven.access.admin.domain.repository;

import com.cnetong.access.admin.domain.entity.MessageBlocking;
import com.cnetong.common.domain.DomainRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface BlockingRepository extends DomainRepository<MessageBlocking, String> {
    long countByListenerId(String channelId);

    @Transactional
    long deleteByListenerId(String listenerId);

    @Modifying
    @Query("delete from MessageBlocking where id=:id")
    void deleteById(@NotNull @Param("id") String id);
}
