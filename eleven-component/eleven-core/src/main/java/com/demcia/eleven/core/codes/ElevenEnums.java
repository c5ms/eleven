package com.demcia.eleven.core.codes;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

/**
 * 枚举类型帮助工具
 *
 * @author wangzc
 */
@UtilityClass
public class ElevenEnums {
    /**
     * 根据字面值，查找枚举
     *
     * @param clazz 枚举类型
     * @param label 字面值
     * @param <E>   枚举类型
     * @return 枚举实例，可能没有找到，则返回空
     */
    @SuppressWarnings("unchecked")
    public <E extends Enum<E>> Optional<E> find(Class<E> clazz, String label) {
        return EnumUtils.getEnumList(clazz)
                .stream()
                .map(e -> (ElevenEnum) e)
                .filter(appLevel -> StringUtils.equals(appLevel.getLabel(), label))
                .map(epeiusEnum -> (E) epeiusEnum)
                .findFirst();
    }


}
