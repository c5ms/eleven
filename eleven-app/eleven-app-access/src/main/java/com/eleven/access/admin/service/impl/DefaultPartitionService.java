package com.eleven.access.admin.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.cnetong.access.admin.domain.entity.MessagePartitionDefinition;
import com.cnetong.access.admin.domain.event.PartitionUpdatedEvent;
import com.cnetong.access.admin.domain.repository.PartitionDefinitionRepository;
import com.cnetong.access.admin.service.PartitionService;
import com.cnetong.access.core.MessageRepository;
import com.cnetong.common.web.ProcessRejectException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class DefaultPartitionService implements PartitionService {
    private final MessageRepository messageRepository;
    private final PartitionDefinitionRepository partitionDefinitionRepository;

    @Override
    public Collection<MessagePartitionDefinition> listPartitions() {
        return partitionDefinitionRepository.findAll(Sort.by(MessagePartitionDefinition.Fields.name).ascending());
    }

    @Override
    @Transactional
    public MessagePartitionDefinition save(MessagePartitionDefinition definition) {
        MessagePartitionDefinition exist;
        if (StringUtils.isNotBlank(definition.getId())) {
            exist = partitionDefinitionRepository.getReferenceById(definition.getId());
            exist.update(definition);
            exist = partitionDefinitionRepository.save(exist);
        } else {
            if (partitionDefinitionRepository.findByName(definition.getName()).isPresent()) {
                throw ProcessRejectException.of("分区名不可重复");
            }
            messageRepository.createPartition(definition.getName());
            exist = partitionDefinitionRepository.save(definition);

        }
        if (exist.getCurrent()) {
            partitionDefinitionRepository.updateCurrent(exist);
            SpringUtil.publishEvent(new PartitionUpdatedEvent());
        }
        return exist;
    }

    @Override
    @Transactional
    public void dropPartition(String id) {
        var exist = partitionDefinitionRepository.getReferenceById(id);
        if (exist.getCurrent()) {
            throw ProcessRejectException.of("当前分区不可删除");
        }
        messageRepository.dropPartition(exist.getName());
        partitionDefinitionRepository.delete(exist);
    }
}
