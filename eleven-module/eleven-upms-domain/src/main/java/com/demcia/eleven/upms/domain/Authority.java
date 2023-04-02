package com.demcia.eleven.upms.domain;

import lombok.Value;


@Value
public class Authority {

    public static String TYPE_ROLE = "role";
    public static String TYPE_RESOURCE = "resource";
    public static String TYPE_PERMISSION = "permission";

    String type;
    String name;

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