package com.eleven.access.admin.domain.repository;

import com.cnetong.access.admin.domain.entity.MessageProducerDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Collection;

public interface ProducerDefinitionRepository extends JpaRepository<MessageProducerDefinition, String>, JpaSpecificationExecutor<MessageProducerDefinition> {

    Collection<MessageProducerDefinition> findAllByRunning(boolean running);

}
