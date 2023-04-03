package com.demcia.eleven.core.generate.support;

import cn.hutool.core.util.IdUtil;
import com.demcia.eleven.core.generate.IdentityGenerator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ObjectIdGenerator implements IdentityGenerator {

    @Override
    public synchronized String next() {
        return IdUtil.objectId();
    }

}
