package com.eleven.framework.data.support;

import cn.hutool.core.util.IdUtil;
import com.eleven.framework.data.IdentityGenerator;

public class UuidGenerator implements IdentityGenerator {

    @Override
    public String next() {
        return IdUtil.simpleUUID();
    }

}
