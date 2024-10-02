package com.eleven.access.admin.service;

import com.cnetong.access.admin.domain.action.TopicDefinitionQueryAction;
import com.cnetong.access.admin.domain.entity.MessageTopicDefinition;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public interface TopicService {

    Collection<MessageTopicDefinition> list(TopicDefinitionQueryAction action);

    Optional<MessageTopicDefinition> find(String id);

    MessageTopicDefinition save(MessageTopicDefinition gateStack);

    void delete(String id);

    MessageTopicDefinition require(String id);
}
