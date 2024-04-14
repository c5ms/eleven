package com.eleven.core.data.support;

import cn.hutool.core.util.IdUtil;
import com.eleven.core.data.IdentityGenerator;

public class UuidGenerator implements IdentityGenerator {

    @Override
    public String next() {
        return IdUtil.simpleUUID();
    }

}
