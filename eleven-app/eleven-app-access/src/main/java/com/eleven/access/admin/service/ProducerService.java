package com.eleven.access.admin.service;

import com.cnetong.access.admin.domain.action.MessageProducerQueryAction;
import com.cnetong.access.admin.domain.entity.MessageProducerDefinition;
import com.cnetong.common.query.domain.Page;

import java.util.Collection;
import java.util.Optional;

public interface ProducerService {

    MessageProducerDefinition require(String id);

    Optional<MessageProducerDefinition> get(String id);

    MessageProducerDefinition save(MessageProducerDefinition messageProducerDefinition);

    MessageProducerDefinition update(MessageProducerDefinition messageProducerDefinition);

    Page<MessageProducerDefinition> query(MessageProducerQueryAction action);

    void delete(MessageProducerDefinition importation);

    MessageProducerDefinition start(MessageProducerDefinition messageProducerDefinition);

    MessageProducerDefinition stop(MessageProducerDefinition messageProducerDefinition);

    Collection<MessageProducerDefinition> list(MessageProducerQueryAction queryAction);
}
