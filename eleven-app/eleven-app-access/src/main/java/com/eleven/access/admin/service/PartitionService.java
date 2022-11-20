package com.eleven.access.admin.service;

import com.cnetong.access.admin.domain.entity.MessagePartitionDefinition;

import java.util.Collection;

public interface PartitionService {

    Collection<MessagePartitionDefinition> listPartitions();

    MessagePartitionDefinition save(MessagePartitionDefinition MessagePartitionDefinition);

    void dropPartition(String partition);
}
