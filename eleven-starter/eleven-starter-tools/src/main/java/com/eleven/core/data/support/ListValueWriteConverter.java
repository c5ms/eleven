package com.eleven.core.data.support;

import cn.hutool.json.JSONUtil;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class ListValueWriteConverter implements Converter<ListValue<String>, String> {

    @Override
    @Nonnull
    public String convert(@Nullable ListValue<String> source) {
        return JSONUtil.toJsonStr(source);
    }
}
