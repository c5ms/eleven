package com.eleven.access.admin.domain.repository;

import com.cnetong.access.admin.domain.entity.MessagePartitionDefinition;
import com.cnetong.common.domain.DomainRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PartitionDefinitionRepository extends DomainRepository<MessagePartitionDefinition, String> {

    @Query("delete MessagePartitionDefinition p where p.name=:name")
    @Modifying
    Integer deleteByName(@Param("name") String name);

    @Query("update MessagePartitionDefinition p set p.current=false where p <> :current")
    @Modifying
    void updateCurrent(@Param("current") MessagePartitionDefinition exist);

    Optional<MessagePartitionDefinition> findByCurrent(@Param("current") boolean current);


    Optional<MessagePartitionDefinition> findByName(@Param("name") String name);
}
