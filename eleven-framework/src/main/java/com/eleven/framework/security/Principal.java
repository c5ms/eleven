package com.eleven.framework.security;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * 一个访问主体
 */
@Data
public class Principal implements Serializable, Identifiable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主体名 通常是账号，ID，等唯一标识
     */
    String name;
    /**
     * 主体类型，比如系统用户，CAS单点登录用户。或者谋其他系统用户，微信用户，某授权外部应用...
     */
    String type;

    public Principal() {
    }

    public Principal(String type, String name) {
        Objects.requireNonNull(name, "name must be not null");
        Objects.requireNonNull(type, "type must be not null");
        this.name = name;
        this.type = type;
    }

    /**
     * 使用标识创建主体
     *
     * @param identify 标识
     */
    public Principal(String identify) {
        Objects.requireNonNull(identify, "identify must be not null");

        int idx = identify.lastIndexOf("#");
        if (idx == -1 || idx == 0) {
            this.name = identify;
            this.type = "unknown";
            return;
        }

        this.type = identify.substring(0, idx);
        this.name = identify.substring(idx + 1);
    }

    /**
     * 换取体的标识
     *
     * @return 主体标识
     */
    @Override
    public String identity() {
        if (this.type.equals("unknown")) {
            return this.name;
        }
        return String.format("%s#%s", type, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Principal principal = (Principal) o;
        return Objects.equals(getName(), principal.getName()) && Objects.equals(getType(), principal.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getType());
    }


}
