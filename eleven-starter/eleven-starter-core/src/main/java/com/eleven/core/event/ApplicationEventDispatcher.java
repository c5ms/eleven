package com.eleven.core.event;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationEventDispatcher {

    private final List<ApplicationEventMessageSender> senders;

    private final ApplicationEventSerializer serializer;

    @EventListener
    public void outbound(ApplicationEvent event) throws ApplicationEventSerializeException {
        if (null == event.getClass().getAnnotation(IntegrationEvent.class)) {
            return;
        }
        if (event.meta().getFrom() != ApplicationEventMeta.From.INTERNAL) {
            return;
        }
        if(log.isDebugEnabled()){
            log.debug("send {} to outbound :{}", event.getClass().getSimpleName(), JSONUtil.toJsonStr(event));
        }
        var message = serializer.serialize(event);
        var json = JSONUtil.toJsonStr(message);
        for (ApplicationEventMessageSender sender : senders) {
            sender.send(json);
        }
    }

    public void dispatch(String content) throws ApplicationEventSerializeException {
        var message = JSONUtil.toBean(content, ApplicationEventMessage.class);
        var eventOpt = serializer.deserialize(message);
        if (eventOpt.isEmpty()) {
            return;
        }

        eventOpt.ifPresent(event -> {
            event.meta().setFrom(ApplicationEventMeta.From.EXTERNAL);
            if(log.isDebugEnabled()){
                log.debug("receive {} from {} :{}", event.getClass().getSimpleName(), message.getService(), JSONUtil.toJsonStr(event));
            }
            SpringUtil.publishEvent(event);
        });
    }

}
