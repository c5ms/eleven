package com.eleven.core.security;

import java.util.Collection;

/**
 * 主体授权器,将认证后的主体对象进行授权。
 */
public interface PrincipalAuthorizerProvider {

    /**
     * 判断一个主体是否受到支持
     *
     * @param principal 要判断的主体
     * @return true 表示支持此主体对象
     */
    boolean support(Principal principal);

    /**
     * 对一个访问对象进行授权
     *
     * @param principal 主体对象
     * @return 授权列表
     */
    Collection<String> authorize(Principal principal);


}
