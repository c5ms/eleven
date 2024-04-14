package com.eleven.access.admin.domain.repository;

import com.cnetong.access.admin.domain.entity.MessageTopicDefinition;
import com.cnetong.common.domain.DomainRepository;

import java.util.Optional;

public interface TopicDefinitionRepository extends DomainRepository<MessageTopicDefinition, String> {

    Optional<MessageTopicDefinition> findByName(String name);
}
