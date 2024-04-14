package com.eleven.access.admin.domain.repository;

import com.cnetong.access.admin.domain.entity.MessageListenerDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Collection;

public interface ListenerDefinitionRepository extends JpaRepository<MessageListenerDefinition, String>, JpaSpecificationExecutor<MessageListenerDefinition> {
    Collection<MessageListenerDefinition> findAllByRunning(boolean running);

}
