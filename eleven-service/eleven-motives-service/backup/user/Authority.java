package com.motiveschina.hotel.features.user;

import com.eleven.framework.security.Identifiable;
import com.eleven.framework.security.Principal;
import com.eleven.framework.security.ToPrincipal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;


@Table("upms_authority")
@Getter
public class Authority extends AbstractEntity {

    public static String POWER_ROLE = "role";
    public static String POWER_RESOURCE = "resource";
    public static String POWER_PERMISSION = "permission";

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

    public static Power powerOf(String type, String name) {
        return new Power(type, name);
    }

    public static Owner ownerOf(String type, String name) {
        return new Owner(type, name);
    }

    public static Owner ownerOf(Principal principal) {
        return new Owner(principal.getType(), principal.getName());
    }

    /**
     * @param toPrincipal an object which can convert to a principal
     * @return -
     * @deprecated not really deprecated, but not recommend to use, we need more thought about whether should create ina imply way
     */
    @Deprecated
    public static Owner ownerOf(ToPrincipal toPrincipal) {
        var principal = toPrincipal.toPrincipal();
        return new Owner(principal.getType(), principal.getName());
    }

    /**
     * 检查当前权限是否属于某个类型
     *
     * @param type 类型
     * @return true 表示 是
     */
    public boolean isPowerTypeBy(String type) {
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
    @NoArgsConstructor
    public static class Power implements Identifiable {

        @Column("power_type")
        private String type;

        @Column("power_name")
        private String name;

        @Override
        public String identity() {
            return String.format("%s#%s", type, name);
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Owner implements Identifiable {

        @Column("owner_type")
        private String type;

        @Column("owner_name")
        private String name;

        @Override
        public String identity() {
            return String.format("%s#%s", type, name);
        }
    }
}
