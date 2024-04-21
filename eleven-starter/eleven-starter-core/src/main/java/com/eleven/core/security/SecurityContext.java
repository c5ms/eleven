package com.eleven.core.security;

import com.eleven.core.security.support.ElevenAuthentication;
import jakarta.annotation.Nonnull;
import lombok.experimental.UtilityClass;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.Set;

@UtilityClass
public class SecurityContext {

    private static final String ROLE_PREFIX = "ROLE_";
    private static final String ADMIN_ROLE_NAME = "admin";
    private static final String ADMIN_ROLE = ROLE_PREFIX + ADMIN_ROLE_NAME;
    private static final String EXCEPTION_MESSAGE = "权限不足";


    /**
     * 是否是匿名用户
     *
     * @return true 表示是匿名用户
     */
    public static boolean isAnonymous() {
        return getCurrentSubject().isAnonymous();
    }

    /**
     * 读取当前认证的令牌
     *
     * @return 当前令牌
     */
    public static Token getToken() {
        return Optional.ofNullable(getAuthentication())
            .filter(authentication -> authentication instanceof ElevenAuthentication)
            .map(authentication -> (Token) authentication.getCredentials())
            .orElse(null);
    }

    /**
     * 读取当前访问身份
     *
     * @return 当前访问身份
     */
    @Nonnull
    public static Subject getCurrentSubject() {
        return Optional.ofNullable(getAuthentication())
            .map(org.springframework.security.core.Authentication::getDetails)
            .filter(Subject.class::isInstance)
            .map(Subject.class::cast)
            .orElse(Subject.ANONYMOUS_INSTANCE);
    }

    /**
     * 当前访问主体
     *
     * @return 当前访问主体
     */
    public static Optional<Principal> getPrincipal() {
        return Optional.ofNullable(getAuthentication())
            .map(org.springframework.security.core.Authentication::getPrincipal)
            .filter(Principal.class::isInstance)
            .map(Principal.class::cast);
    }


    /**
     * 读取所有的权限
     *
     * @return 权限集
     */
    public static Set<String> getAuthorities() {
        return getCurrentSubject().getAuthorities();
    }


    /**
     * 是够拥有某角色
     *
     * @param role 角色
     * @return true 表示拥有
     */
    public static boolean checkHasRole(String role) {
        return getCurrentSubject().getAuthorities().contains(ROLE_PREFIX + role);
    }


    /**
     * 是够拥有某权限
     *
     * @param auth 权限
     * @return true 表示拥有
     */
    public static boolean checkHasAuthorities(String auth) {
        return getCurrentSubject().getAuthorities().contains(auth);
    }

    /**
     * 是否是管理员
     *
     * @return true 表示是
     */
    public static boolean isAdmin() {
        return checkHasAuthorities(ADMIN_ROLE);
    }

    /**
     * 是否不是管理员
     *
     * @return true 表示不是管理员
     */
    public static boolean isNotAdmin() {
        return !isAdmin();
    }


    /**
     * 当前访问主体名
     *
     * @return 主体名称
     */
    public static Optional<String> getPrincipalName() {
        return getPrincipal().map(Principal::getName);
    }


    /**
     * 获取当前访问主体，如果不存在，则抛出错误
     *
     * @return 当前访问主体
     */
    public static Principal requirePrincipal() {
        return getPrincipal().orElseThrow(() -> new AccessDeniedException(EXCEPTION_MESSAGE));
    }

    /**
     * 当前访问授权
     *
     * @return 当前访问授权
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }


    /**
     * 设置在访问授权
     */
    public static void setAuthentication(org.springframework.security.core.Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


}
