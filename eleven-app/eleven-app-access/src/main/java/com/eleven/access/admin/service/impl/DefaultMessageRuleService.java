package com.eleven.access.admin.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.cnetong.access.admin.domain.action.MessageRuleQueryAction;
import com.cnetong.access.admin.domain.entity.MessageRuleDefinition;
import com.cnetong.access.admin.domain.event.TopicUpdatedEvent;
import com.cnetong.access.admin.domain.repository.ProducerDefinitionRepository;
import com.cnetong.access.admin.domain.repository.RouteDefinitionRepository;
import com.cnetong.access.admin.service.MessageRuleService;
import com.cnetong.access.admin.support.AccessFactory;
import com.cnetong.common.query.domain.Page;
import com.cnetong.common.query.domain.QueryHelper;
import com.cnetong.common.query.domain.QuerySort;
import com.cnetong.common.web.ProcessRejectException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultMessageRuleService implements MessageRuleService {
    private final RouteDefinitionRepository routeDefinitionRepository;
    private final ProducerDefinitionRepository producerDefinitionRepository;
    private final AccessFactory accessFactory;

    @Override
    public MessageRuleDefinition require(String id) {
        return routeDefinitionRepository.findById(id).orElseThrow(() -> ProcessRejectException.of("连接不存在"));
    }

    @Override
    public Optional<MessageRuleDefinition> get(String id) {
        return routeDefinitionRepository.findById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MessageRuleDefinition save(MessageRuleDefinition messageRuleDefinition) {
        messageRuleDefinition.setError(null);
        //  修改
        if (StringUtils.isNotBlank(messageRuleDefinition.getId())) {
            MessageRuleDefinition exist = this.require(messageRuleDefinition.getId());
            BeanUtils.copyProperties(messageRuleDefinition, exist);
            if (StringUtils.isNotBlank(exist.getProducerId())) {
                exist.setProducer(producerDefinitionRepository.getReferenceById(exist.getProducerId()));
            }
            validate(exist);
            SpringUtil.publishEvent(new TopicUpdatedEvent(messageRuleDefinition.getTopic()));
            return routeDefinitionRepository.save(exist);
        }
        SpringUtil.publishEvent(new TopicUpdatedEvent(messageRuleDefinition.getTopic()));
        if (StringUtils.isNotBlank(messageRuleDefinition.getProducerId())) {
            messageRuleDefinition.setProducer(producerDefinitionRepository.getReferenceById(messageRuleDefinition.getProducerId()));
        }
        validate(messageRuleDefinition);
        return routeDefinitionRepository.save(messageRuleDefinition);
    }

    private void validate(MessageRuleDefinition exist) {
        try {
            accessFactory.createRule(exist);
        } catch (Exception e) {
            exist.setError(StringUtils.substring(ExceptionUtils.getRootCauseMessage(e), 0, 500));
        }
    }

    @Override
    public MessageRuleDefinition update(MessageRuleDefinition messageRuleDefinition) {
        return routeDefinitionRepository.save(messageRuleDefinition);
    }

    @Override
    public List<MessageRuleDefinition> listRunning() {
        return routeDefinitionRepository.findByPublishedOrderByOrderAsc(true);
    }


    @Override
    public Page<MessageRuleDefinition> query(MessageRuleQueryAction action) {
        action.sortBy(MessageRuleDefinition.Fields.order, QuerySort.Direction.ASC);
        var page = routeDefinitionRepository.findAll(action.toSpecification(), action.toPageable());
        return QueryHelper.toPageList(page);
    }

    @Override
    public void delete(MessageRuleDefinition messageRuleDefinition) {
        routeDefinitionRepository.delete(messageRuleDefinition);
    }

    @Override
    public void publishRoute(MessageRuleDefinition messageRuleDefinition) {
        messageRuleDefinition.setPublished(true);
        messageRuleDefinition.setError(null);
        routeDefinitionRepository.save(messageRuleDefinition);
        SpringUtil.publishEvent(new TopicUpdatedEvent(messageRuleDefinition.getTopic()));
    }

    @Override
    public void cancelRoute(MessageRuleDefinition messageRuleDefinition) {
        messageRuleDefinition.setPublished(false);
        messageRuleDefinition.setError(null);
        routeDefinitionRepository.save(messageRuleDefinition);
        SpringUtil.publishEvent(new TopicUpdatedEvent(messageRuleDefinition.getTopic()));
    }

}
