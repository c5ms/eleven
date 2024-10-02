package com.eleven.access.admin.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.cnetong.access.admin.domain.action.MessageListenerQueryAction;
import com.cnetong.access.admin.domain.entity.MessageListenerDefinition;
import com.cnetong.access.admin.domain.event.ListenerUpdatedEvent;
import com.cnetong.access.admin.domain.repository.ListenerDefinitionRepository;
import com.cnetong.access.admin.service.ListenerService;
import com.cnetong.common.query.domain.Page;
import com.cnetong.common.query.domain.QueryHelper;
import com.cnetong.common.web.ProcessRejectException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultListenerService implements ListenerService {

    private final ListenerDefinitionRepository producerDefinitionRepository;

    @Override
    public MessageListenerDefinition require(String id) {
        return producerDefinitionRepository.findById(id).orElseThrow(() -> ProcessRejectException.of("终端不存在"));
    }

    @Override
    public Optional<MessageListenerDefinition> get(String id) {
        return producerDefinitionRepository.findById(id);
    }


    @Override
    public Page<MessageListenerDefinition> query(MessageListenerQueryAction action) {
        var page = producerDefinitionRepository.findAll(action.toSpecification(), action.toPageable());
        return QueryHelper.toPageList(page);
    }

    @Override
    public void delete(MessageListenerDefinition producerDefinition) {
        producerDefinitionRepository.delete(producerDefinition);
        SpringUtil.publishEvent(new ListenerUpdatedEvent(producerDefinition.getId()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MessageListenerDefinition save(MessageListenerDefinition producerDefinition) {
        // 修改
        if (producerDefinition.hasId()) {
            MessageListenerDefinition exist = this.require(producerDefinition.getId());
            exist.update(producerDefinition);
            producerDefinitionRepository.save(exist);
            SpringUtil.publishEvent(new ListenerUpdatedEvent(producerDefinition.getId()));
            return exist;
        }
        // 创建
        producerDefinitionRepository.save(producerDefinition);
        SpringUtil.publishEvent(new ListenerUpdatedEvent(producerDefinition.getId()));

        return producerDefinition;
    }

    @Override
    public MessageListenerDefinition update(MessageListenerDefinition producerDefinition) {
        SpringUtil.publishEvent(new ListenerUpdatedEvent(producerDefinition.getId()));
        return producerDefinitionRepository.save(producerDefinition);
    }

    @Override
    public Collection<MessageListenerDefinition> list(MessageListenerQueryAction queryAction) {
        return producerDefinitionRepository.findAll(queryAction.toSpecification());
    }

}
