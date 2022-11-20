package com.eleven.access.core;

import java.util.Map;

public interface ComponentFactory<Component> {

    ComponentSpecification getSpecification();

    Component create(Map<String, String> config) throws ComponentConfigException;


}
