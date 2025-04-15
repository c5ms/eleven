package com.eleven.framework.domain.support;

import cn.hutool.core.util.IdUtil;
import com.eleven.framework.domain.IdentityGenerator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ObjectIdGenerator implements IdentityGenerator {

    @Override
    public synchronized String next() {
        return IdUtil.objectId();
    }

}
