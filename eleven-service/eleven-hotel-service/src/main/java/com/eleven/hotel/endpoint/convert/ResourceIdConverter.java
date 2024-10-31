package com.eleven.hotel.endpoint.convert;

import com.eleven.hotel.endpoint.value.ResourceId;
import jakarta.annotation.Nonnull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ResourceIdConverter implements Converter<String, ResourceId> {

    @Override
    public ResourceId convert(@Nonnull String from) {
        var dig = StringUtils.getDigits(from);
        if (StringUtils.isBlank(dig)) {
            return null;
        }
        return new ResourceId(Integer.parseInt(dig));
    }
}
