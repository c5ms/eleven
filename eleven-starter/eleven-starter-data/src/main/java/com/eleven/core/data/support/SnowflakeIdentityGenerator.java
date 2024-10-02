package com.eleven.core.data.support;


import cn.hutool.core.lang.Snowflake;
import com.eleven.core.data.IdentityGenerator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SnowflakeIdentityGenerator implements IdentityGenerator {

    private final Snowflake snowflake;

    @Override
    public String next() {
        return snowflake.nextIdStr();
    }
}
