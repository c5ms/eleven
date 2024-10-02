package com.eleven.access.admin.service;

import com.cnetong.access.admin.domain.action.MessageListenerQueryAction;
import com.cnetong.access.admin.domain.entity.MessageListenerDefinition;
import com.cnetong.common.query.domain.Page;

import java.util.Collection;
import java.util.Optional;

public interface ListenerService {

    MessageListenerDefinition require(String id);

    Optional<MessageListenerDefinition> get(String id);

    MessageListenerDefinition save(MessageListenerDefinition producerDefinition);

    MessageListenerDefinition update(MessageListenerDefinition producerDefinition);

    Page<MessageListenerDefinition> query(MessageListenerQueryAction action);

    void delete(MessageListenerDefinition importation);

    Collection<MessageListenerDefinition> list(MessageListenerQueryAction queryAction);
}
