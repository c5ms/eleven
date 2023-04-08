package com.eleven.core.generate.support;

import cn.hutool.core.util.IdUtil;
import com.eleven.core.generate.IdentityGenerator;

public class UuidGenerator implements IdentityGenerator {

    @Override
    public String next() {
        return IdUtil.simpleUUID();
    }

}
