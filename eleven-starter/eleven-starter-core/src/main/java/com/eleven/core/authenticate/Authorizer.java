package com.eleven.core.authenticate;

import java.util.Collection;

/**
 * 主体授权器,将认证后的主体对象进行授权。
 */
public interface Authorizer {

    /**
     * 对一个访问对象进行授权
     *
     * @param principal 主体对象
     * @return 授权列表
     */
    Collection<String> authorize(Principal principal);


}
