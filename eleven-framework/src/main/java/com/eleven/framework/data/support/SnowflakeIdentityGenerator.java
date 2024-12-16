package com.eleven.framework.data.support;


import cn.hutool.core.lang.Snowflake;
import com.eleven.framework.data.IdentityGenerator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SnowflakeIdentityGenerator implements IdentityGenerator {

    private final Snowflake snowflake;

    @Override
    public String next() {
        return snowflake.nextIdStr();
    }
}
