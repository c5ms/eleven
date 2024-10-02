package com.eleven.access.core;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@EqualsAndHashCode(of = {"uri"})
public class ComponentSpecification {

    private final URI uri;
    private final String type;
    private final List<Property> properties = new ArrayList<>();
    private final List<Property> runtimes = new ArrayList<>();
    private final List<String> description = new ArrayList<>();
    private String label;

    private ComponentSpecification(Class<?> type, String name) {
        this.type = type.getSimpleName();
        this.uri = URI.create(String.format("%s/%s", getType(), name));
    }

    public static ComponentSpecification of(Class<?> type, String name) {
        return new ComponentSpecification(type, name);
    }

    public static Property property(String name, PropertyType type) {
        return new Property(name, type);
    }

    public static Property property(String group, String name, PropertyType type) {
        return new Property(group, name, type);
    }

    public ComponentSpecification property(Property... property) {
        this.properties.addAll(Arrays.asList(property));
        return this;
    }

    public ComponentSpecification runtime(Property... property) {
        this.runtimes.addAll(Arrays.asList(property));
        return this;
    }

    public ComponentSpecification describe(String description) {
        this.description.add(description);
        return this;
    }

    public ComponentSpecification label(String label) {
        this.label = label;
        return this;
    }

    public enum PropertyType {
        string,
        bool,
        date,
        datetime,
        time,
        number,
        password,
        sql,
        message_producer,
        message_topic;
    }

    @Getter
    @Accessors(chain = true)
    @EqualsAndHashCode(of = {"name", "group"})
    public static class Property {
        private final String name;
        private final PropertyType type;

        private String tip;
        private String label;
        private String defaults;
        private boolean required = false;
        private String placeholder;
        private String group;
        private boolean stretch = false;

        public Property(String name, PropertyType type) {
            this.group = "root";
            this.name = name;
            this.type = type;
        }

        public Property(String group, String name, PropertyType type) {
            this.group = group;
            this.name = name;
            this.type = type;
        }

        public Property withDefault(String defaults) {
            this.defaults = defaults;
            return this;
        }

        public Property withTip(String tip) {
            this.tip = tip;
            return this;
        }

        public Property withPlaceholder(String placeholder) {
            this.placeholder = placeholder;
            return this;
        }

        public Property withLabel(String label) {
            this.label = label;
            return this;
        }

        public Property group(String group) {
            this.group = group;
            return this;
        }

        public Property required(boolean required) {
            this.required = required;
            return this;
        }

        public Property stretch(boolean stretch) {
            this.stretch = stretch;
            return this;
        }
    }
}
