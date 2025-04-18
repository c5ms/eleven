package com.eleven.framework.event.support;

import cn.hutool.core.util.StrUtil;
import com.eleven.framework.event.*;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class JacksonApplicationEventSerializer implements ApplicationEventSerializer {

    private final ObjectMapper objectMapper;

    private final Map<String, Class<? extends ApplicationEvent>> eventClasses = new ConcurrentHashMap<>();
    private final Map<Class<? extends ApplicationEvent>, String> eventNames = new ConcurrentHashMap<>();

    public JacksonApplicationEventSerializer() {
        this.objectMapper = JsonMapper.builder()
                .configure(MapperFeature.USE_ANNOTATIONS, true)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .serializationInclusion(JsonInclude.Include.USE_DEFAULTS)
                .visibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
                .addModules(new Jdk8Module(), new JavaTimeModule())
                .build();
    }


    @Override
    public ApplicationEventMessage serialize(ApplicationEvent event) throws ApplicationEventSerializeException {
        try {
            ApplicationEventMessage message = new ApplicationEventMessage();
            message.setCls(event.getClass().getName());
            message.setTime(event.getHeader().getTime());
            message.setTrigger(event.getHeader().getTrigger());
            message.setEvent(eventNames.computeIfAbsent(event.getClass(), this::getEventName));
            message.setBody(objectMapper.writeValueAsString(event));
            return message;
        } catch (JsonProcessingException e) {
            throw new ApplicationEventSerializeException("fail to serialize event", e);
        }
    }

    /**
     * deserialize the message into domain events.
     *
     * @param message the domain event message
     * @return the domain event null if the event class is not existing in the classpath
     * @throws ApplicationEventSerializeException when message body is broken.
     */
    @Override
    public Optional<ApplicationEvent> deserialize(ApplicationEventMessage message) throws ApplicationEventSerializeException {
        try {
            var clazz = eventClasses.computeIfAbsent(message.getCls(), this::tryGetEventClass);
            if (!StringUtils.equals(getEventName(clazz), message.getEvent())) {
                return Optional.empty();
            }
            if (clazz == ApplicationEvent.class) {
                return Optional.empty();
            }
            var event = objectMapper.readValue(message.getBody(), clazz);
            message.writeHeaderTo(event);
            return Optional.of(event);
        } catch (JsonProcessingException e) {
            throw new ApplicationEventSerializeException("fail to serialize event", e);
        }
    }

    public String getEventName(Class<? extends ApplicationEvent> clazz) {
        var name = tryGetNameByAnnotation(clazz);
        if (null != name) {
            return name;
        }
        String eventName = ClassUtils.getSimpleName(clazz);
        eventName = StringUtils.removeEnd(eventName, "Event");
        eventName = StrUtil.toUnderlineCase(eventName);
        return StringUtils.uncapitalize(eventName);
    }

    @SuppressWarnings("unchecked")
    private Class<? extends ApplicationEvent> tryGetEventClass(String className) {
        try {
            return (Class<? extends ApplicationEvent>) Class.forName(className);
        } catch (ClassNotFoundException e) {
            return ApplicationEvent.class;
        }
    }

    private String tryGetNameByAnnotation(Class<? extends ApplicationEvent> clazz) {
        var outbound = clazz.getAnnotation(IntegrationEvent.class);
        if (null != outbound) {
            var name = outbound.value();
            if (StringUtils.isNotBlank(name)) {
                return name;
            }
        }
        return null;
    }

}
