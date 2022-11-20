package com.eleven.access.admin.domain.repository;

import com.cnetong.access.admin.domain.entity.MessageRuleDefinition;
import com.cnetong.common.domain.DomainRepository;

import java.util.List;

public interface RouteDefinitionRepository extends DomainRepository<MessageRuleDefinition, String> {

    List<MessageRuleDefinition> findByPublishedOrderByOrderAsc(boolean published);
}
