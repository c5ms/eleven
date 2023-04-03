package com.demcia.eleven.core.generate.support;

import cn.hutool.core.util.IdUtil;
import com.demcia.eleven.core.generate.IdentityGenerator;

public class NanoIdGenerator implements IdentityGenerator {

    @Override
    public String next() {
        return IdUtil.nanoId();
    }

}
