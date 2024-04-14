package com.eleven.access.admin.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.cnetong.access.admin.domain.action.MessageProducerQueryAction;
import com.cnetong.access.admin.domain.entity.MessageProducerDefinition;
import com.cnetong.access.admin.domain.event.ProducerUpdatedEvent;
import com.cnetong.access.admin.domain.repository.ProducerDefinitionRepository;
import com.cnetong.access.admin.service.ProducerService;
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
public class DefaultProducerService implements ProducerService {

    private final ProducerDefinitionRepository producerDefinitionRepository;

    @Override
    public MessageProducerDefinition require(String id) {
        return producerDefinitionRepository.findById(id).orElseThrow(() -> ProcessRejectException.of("终端不存在"));
    }

    @Override
    public Optional<MessageProducerDefinition> get(String id) {
        return producerDefinitionRepository.findById(id);
    }


    @Override
    public Page<MessageProducerDefinition> query(MessageProducerQueryAction action) {
        var page = producerDefinitionRepository.findAll(action.toSpecification(), action.toPageable());
        return QueryHelper.toPageList(page);
    }

    @Override
    public void delete(MessageProducerDefinition messageProducerDefinition) {
        producerDefinitionRepository.delete(messageProducerDefinition);
        SpringUtil.publishEvent(new ProducerUpdatedEvent(messageProducerDefinition.getId()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MessageProducerDefinition save(MessageProducerDefinition messageProducerDefinition) {
        // 修改
        if (messageProducerDefinition.hasId()) {
            MessageProducerDefinition exist = this.require(messageProducerDefinition.getId());
            exist.update(messageProducerDefinition);
            producerDefinitionRepository.save(exist);
            SpringUtil.publishEvent(new ProducerUpdatedEvent(messageProducerDefinition.getId()));
            return exist;
        }
        // 创建
        producerDefinitionRepository.save(messageProducerDefinition);
        SpringUtil.publishEvent(new ProducerUpdatedEvent(messageProducerDefinition.getId()));
        return messageProducerDefinition;
    }

    @Override
    public MessageProducerDefinition update(MessageProducerDefinition messageProducerDefinition) {
        SpringUtil.publishEvent(new ProducerUpdatedEvent(messageProducerDefinition.getId()));
        return producerDefinitionRepository.save(messageProducerDefinition);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MessageProducerDefinition start(MessageProducerDefinition messageProducerDefinition) {
        messageProducerDefinition.setRunning(true);
        return save(messageProducerDefinition);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MessageProducerDefinition stop(MessageProducerDefinition messageProducerDefinition) {
        messageProducerDefinition.setRunning(false);
        return save(messageProducerDefinition);
    }

    @Override
    public Collection<MessageProducerDefinition> list(MessageProducerQueryAction queryAction) {
        return producerDefinitionRepository.findAll(queryAction.toSpecification());
    }

}
