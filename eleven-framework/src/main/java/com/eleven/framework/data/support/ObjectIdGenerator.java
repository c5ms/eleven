package com.eleven.framework.data.support;

import cn.hutool.core.util.IdUtil;
import com.eleven.framework.data.IdentityGenerator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ObjectIdGenerator implements IdentityGenerator {

    @Override
    public synchronized String next() {
        return IdUtil.objectId();
    }

}
