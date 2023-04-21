package com.eleven.upms.domain;

public record Authority(String type, String name) {

    public static String TYPE_ROLE = "role";
    public static String TYPE_RESOURCE = "resource";
    public static String TYPE_PERMISSION = "permission";

    public static Authority of(String type, String name) {
        return new Authority(type, name);
    }

    public static Authority ofRole(String name) {
        return new Authority(TYPE_ROLE, name);
    }

    public static Authority ofPermission(String name) {
        return new Authority(TYPE_PERMISSION, name);
    }

    public static Authority ofResource(String name) {
        return new Authority(TYPE_RESOURCE, name);
    }

}