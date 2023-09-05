package com.eleven.upms.domain;

import com.eleven.core.domain.AbstractDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;


@Table("upms_authority")
@Getter
//@AllArgsConstructor(onConstructor = @__({@PersistenceCreator}))
public class Authority extends AbstractDomain<Authority> {

    public static String POWER_ROLE = "role";
    public static String POWER_RESOURCE = "resource";
    public static String POWER_PERMISSION = "permission";

    public static String OWNER_USER = "user";
    @Embedded.Nullable
    private final Owner owner;
    @Embedded.Nullable
    private final Power power;
    @Id
    @Column("id")
    private String id;


    @PersistenceCreator
    public Authority(String id, Owner owner, Power power) {
        this.id = id;
        this.owner = owner;
        this.power = power;
    }

    public static Power powerOfRole(String name) {
        return new Power(POWER_ROLE, name);
    }

    public static Power powerOfPermission(String name) {
        return new Power(POWER_PERMISSION, name);
    }

    public static Power powerOfResource(String name) {
        return new Power(POWER_RESOURCE, name);
    }

    public static Owner powerOf(String type, String name) {
        return new Owner(type, name);
    }

    public static Owner ownerOfUser(String name) {
        return new Owner(OWNER_USER, name);
    }

    /**
     * 检查当前权限是否属于某个类型
     *
     * @param type 类型
     * @return true 表示 是
     */
    public boolean isStuffTypeBy(String type) {
        if (this.power == null) {
            return false;
        }
        return StringUtils.equals(this.power.type, type);
    }

    /**
     * 检查当前拥有者是否属于某个类型
     *
     * @param type 类型
     * @return true 表示 是
     */
    public boolean isOwnerTypeBy(String type) {
        if (this.owner == null) {
            return false;
        }
        return StringUtils.equals(this.owner.type, type);
    }

    @Getter
    @AllArgsConstructor
    public static class Power {

        @Column("power_type")
        private String type;

        @Column("power_name")
        private String name;

    }

    @Getter
    @AllArgsConstructor
    public static class Owner {


        @Column("owner_type")
        private String type;

        @Column("owner_name")
        private String name;

    }
}
