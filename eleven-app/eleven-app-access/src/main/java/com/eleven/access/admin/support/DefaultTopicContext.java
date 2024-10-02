package com.eleven.access.admin.support;

import com.cnetong.access.admin.constants.AccessConstants;
import com.cnetong.access.admin.domain.entity.MessageTopicDefinition;
import com.cnetong.access.admin.domain.repository.TopicDefinitionRepository;
import com.cnetong.access.core.Topic;
import com.cnetong.access.core.TopicContext;
import com.cnetong.common.cluster.MetadataManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultTopicContext implements TopicContext {

    private final AccessFactory accessFactory;
    private final MetadataManager metadataManager;
    private final TopicDefinitionRepository topicDefinitionRepository;
    private final Map<String, Topic> instances = new ConcurrentHashMap<>();

    @Override
    public Optional<Topic> getTopic(String name) {
        metadataManager.syncVersion(AccessConstants.getTopicKey(name), s -> this.load(name));
        return Optional.ofNullable(instances.get(name));
    }


    private void load(String name) {
        log.info("刷新主题，{}", name);
        topicDefinitionRepository.findByName(name)
                .filter(MessageTopicDefinition::getRunning)
                .map(accessFactory::createTopic)
                .ifPresentOrElse(topic -> instances.put(name, topic), () -> instances.remove(name));
    }

}
