package com.eleven.framework.web;

import jakarta.annotation.Nonnull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ResourceIdConverter implements Converter<String, ResourceId> {
    private static final Pattern PATTERN = Pattern.compile("^[a-zA-Z0-9_\\-]+$");

    @Override
    public ResourceId convert(@Nonnull String original) {
        if(!PATTERN.asMatchPredicate().test(original)) {
            return null;
        }
        var id =Long.parseLong(original);
        return new ResourceId(id);
    }
}
