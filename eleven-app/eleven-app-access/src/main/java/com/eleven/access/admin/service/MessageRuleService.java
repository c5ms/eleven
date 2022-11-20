package com.eleven.access.admin.service;

import com.cnetong.access.admin.domain.action.MessageRuleQueryAction;
import com.cnetong.access.admin.domain.entity.MessageRuleDefinition;
import com.cnetong.common.query.domain.Page;

import java.util.List;
import java.util.Optional;

public interface MessageRuleService {

    MessageRuleDefinition require(String id);

    Optional<MessageRuleDefinition> get(String id);

    MessageRuleDefinition save(MessageRuleDefinition messageRuleDefinition);

    MessageRuleDefinition update(MessageRuleDefinition messageRuleDefinition);

    List<MessageRuleDefinition> listRunning();

    Page<MessageRuleDefinition> query(MessageRuleQueryAction action);

    void delete(MessageRuleDefinition importation);

    void publishRoute(MessageRuleDefinition messageRuleDefinition);

    void cancelRoute(MessageRuleDefinition messageRuleDefinition);
}
