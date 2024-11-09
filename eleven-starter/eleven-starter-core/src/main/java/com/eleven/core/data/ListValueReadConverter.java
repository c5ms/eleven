package com.eleven.core.data;

import cn.hutool.json.JSONUtil;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.util.ArrayList;
import java.util.Arrays;

@ReadingConverter
public class ListValueReadConverter implements Converter<String, ListValue<String>> {

    private final static String PLAN_TEXT_SPLITTER = ";";

    @Override
    @Nonnull
    public ListValue<String> convert(@Nullable String source) {
        if (StringUtils.isBlank(source)) {
            return ListValue.of(new ArrayList<>());
        }
        if (source.startsWith("[") && source.endsWith("]")) {
            return ListValue.of(JSONUtil.toList(source, String.class));
        }
        return ListValue.of(Arrays.asList(StringUtils.split(source, PLAN_TEXT_SPLITTER)));
    }
}
