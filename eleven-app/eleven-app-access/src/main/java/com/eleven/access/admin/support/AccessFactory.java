package com.eleven.access.admin.support;

import com.cnetong.access.admin.domain.entity.MessageRuleDefinition;
import com.cnetong.access.admin.domain.entity.MessageTopicDefinition;
import com.cnetong.access.core.*;
import com.cnetong.access.standard.filter.TopicMessageFilterFactory;
import com.cnetong.common.web.ProcessRejectException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccessFactory {
    private final ApplicationContext applicationContext;

    public MessageRule createRule(MessageRuleDefinition definition) {
        var predicates = definition.getFilters().stream()
                .map(StringUtils::trim)
                .filter(StringUtils::isNotBlank)
                .map(this::createFilters)
                .collect(Collectors.toList());

        predicates.add(applicationContext.getBean(TopicMessageFilterFactory.class).apply(definition.getTopic()));

        return MessageRule.builder()
                .filters(
                        predicates
                )
                .processors(
                        definition.getProcessors().stream()
                                .map(StringUtils::trim)
                                .filter(StringUtils::isNotBlank)
                                .map(this::createFilter)
                                .collect(Collectors.toList())
                )
                .producer(definition.getProducerId())
                .build();
    }

    public MessageProcessor createFilter(String directive) {
        directive = StringUtils.trim(directive);
        String name = directive;
        String config = "";
        if (directive.contains("=")) {
            name = StringUtils.trim(directive.substring(0, directive.indexOf("=")));
            config = StringUtils.trim(directive.substring(directive.indexOf("=") + 1));
        }
        Collection<MessageProcessorFactory> routeFilterFactories = applicationContext.getBeansOfType(MessageProcessorFactory.class).values();
        for (MessageProcessorFactory factory : routeFilterFactories) {
            if (StringUtils.equalsIgnoreCase(factory.getName(), name)) {
                return factory.apply(config);
            }
        }
        throw ProcessRejectException.of("路由过滤器解析失败：，没有找到[" + name + "]");
    }

    public MessageFilter createFilters(String directive) {
        directive = StringUtils.trim(directive);
        String name = directive;
        String config = "";
        if (directive.contains("=")) {
            name = StringUtils.trim(directive.substring(0, directive.indexOf("=")));
            config = StringUtils.trim(directive.substring(directive.indexOf("=") + 1));
        }
        Collection<MessageFilterFactory> routePredicateFactories = applicationContext.getBeansOfType(MessageFilterFactory.class).values();
        for (MessageFilterFactory factory : routePredicateFactories) {
            if (StringUtils.equalsIgnoreCase(factory.getName(), name)) {
                return factory.apply(config);
            }
        }
        throw ProcessRejectException.of("路由规则解析失败：，没有找到[" + name + "]");
    }

    public Topic createTopic(MessageTopicDefinition definition) {
        return Topic.builder()
                .topic(definition.getName())
                .rules(definition.getRules().stream().filter(MessageRuleDefinition::isPublished).map(this::createRule).collect(Collectors.toList()))
                .build();

    }

}
