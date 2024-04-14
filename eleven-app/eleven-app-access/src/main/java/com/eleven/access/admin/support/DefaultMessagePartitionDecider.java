package com.eleven.access.admin.support;

import com.cnetong.access.admin.constants.AccessConstants;
import com.cnetong.access.admin.domain.entity.MessagePartitionDefinition;
import com.cnetong.access.admin.domain.repository.PartitionDefinitionRepository;
import com.cnetong.access.core.Message;
import com.cnetong.access.core.MessagePartitionDecider;
import com.cnetong.common.cluster.MetadataManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultMessagePartitionDecider implements MessagePartitionDecider {
    private final PartitionDefinitionRepository partitionDefinitionRepository;
    private final MetadataManager metadataManager;
    private volatile String name;

    @Override
    public String partition(Message message) {
        metadataManager.syncVersion(AccessConstants.VERSION_KEY_PARTITION, s -> {
            var current = partitionDefinitionRepository.findByCurrent(true);
            this.name = current.map(MessagePartitionDefinition::getName).orElse(null);
        });
        return name;
    }
}
