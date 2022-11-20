package com.eleven.access.core;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComponentManager {
    private final ApplicationContext applicationContext;

    /**
     * 列出所有的组件
     *
     * @return 所有组件
     */
    public Set<ComponentSpecification> components() {
        Collection<ComponentFactory> factories = applicationContext.getBeansOfType(ComponentFactory.class).values();
        return factories.stream()
                .map(ComponentFactory::getSpecification)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    public <C> C createComponent(String uri, Map<String, String> config, Class<? extends ComponentFactory<C>> tClass) throws ComponentNotFoundException, ComponentConfigException {
        Collection<? extends ComponentFactory<C>> factories = applicationContext.getBeansOfType(tClass).values();
        return factories.stream()
                .filter(cComponentFactory -> null != cComponentFactory.getSpecification())
                .filter(component -> component.getSpecification().getUri().getPath().equals(uri))
                .findFirst()
                .map(factory -> factory.create(config))
                .orElseThrow(() -> new ComponentNotFoundException("没有找到组件:" + uri));
    }


}
