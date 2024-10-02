package com.eleven.access.admin.support;

import com.cnetong.access.admin.constants.AccessConstants;
import com.cnetong.access.admin.domain.event.*;
import com.cnetong.common.cluster.MetadataManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccessListener {
    private final MetadataManager metadataManager;

    @EventListener(ConnectionUpdatedEvent.class)
    public void onConnectionUpdatedEvent(ConnectionUpdatedEvent event) {
        metadataManager.updateGlobalVersion(AccessConstants.VERSION_KEY_CONNECTIONS);
        metadataManager.updateGlobalVersion(AccessConstants.getResourceKey(event.getId()));
    }

    @EventListener(ProducerUpdatedEvent.class)
    public void onEndpointUpdatedEvent(ProducerUpdatedEvent event) {
        metadataManager.updateGlobalVersion(AccessConstants.VERSION_KEY_PRODUCERS);
        metadataManager.updateGlobalVersion(AccessConstants.getProducerKey(event.getId()));
    }

    @EventListener(ListenerUpdatedEvent.class)
    public void onListenerUpdatedEvent(ListenerUpdatedEvent event) {
        metadataManager.updateGlobalVersion(AccessConstants.VERSION_KEY_LISTENERS);
        metadataManager.updateGlobalVersion(AccessConstants.getListenerKey(event.getId()));
    }

    @EventListener(TopicUpdatedEvent.class)
    public void onMessageRuleUpdatedEvent(TopicUpdatedEvent event) {
        metadataManager.updateGlobalVersion(AccessConstants.VERSION_KEY_TOPICS);
        metadataManager.updateGlobalVersion(AccessConstants.getTopicKey(event.getName()));
    }

    @EventListener(PartitionUpdatedEvent.class)
    public void onPartitionUpdatedEvent(PartitionUpdatedEvent event) {
        metadataManager.updateGlobalVersion(AccessConstants.VERSION_KEY_PARTITION);
    }


}
