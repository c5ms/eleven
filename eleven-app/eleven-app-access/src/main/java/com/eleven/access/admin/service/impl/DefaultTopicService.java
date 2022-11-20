package com.eleven.access.admin.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.cnetong.access.admin.domain.action.TopicDefinitionQueryAction;
import com.cnetong.access.admin.domain.entity.MessageTopicDefinition;
import com.cnetong.access.admin.domain.event.TopicUpdatedEvent;
import com.cnetong.access.admin.domain.repository.TopicDefinitionRepository;
import com.cnetong.access.admin.service.TopicService;
import com.cnetong.common.web.ProcessRejectException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultTopicService implements TopicService {

    private final TopicDefinitionRepository topicDefinitionRepository;

    @Override
    public Collection<MessageTopicDefinition> list(TopicDefinitionQueryAction action) {
        var sort = Sort.by(MessageTopicDefinition.Fields.name).ascending();
        return topicDefinitionRepository.findAll(sort);
    }

    @Override
    public Optional<MessageTopicDefinition> find(String id) {
        return topicDefinitionRepository.findById(id);
    }

    @Override
    public MessageTopicDefinition save(MessageTopicDefinition definition) {
        //  修改
        if (definition.hasId()) {
            var exist = this.require(definition.getId());
            exist.update(definition);
            doSave(exist);
            SpringUtil.publishEvent(new TopicUpdatedEvent(definition.getName()));
            return exist;
        }
        // 新增
        doSave(definition);
        SpringUtil.publishEvent(new TopicUpdatedEvent(definition.getName()));
        return definition;
    }

    private void doSave(MessageTopicDefinition definition) {
        topicDefinitionRepository.save(definition);
    }


    @Override
    public void delete(String id) {
        topicDefinitionRepository.findById(id)
                .ifPresent(topicDefinitionRepository::delete);
    }

    @Override
    public MessageTopicDefinition require(String id) {
        return find(id).orElseThrow(() -> ProcessRejectException.of("主题不存在"));
    }
}
