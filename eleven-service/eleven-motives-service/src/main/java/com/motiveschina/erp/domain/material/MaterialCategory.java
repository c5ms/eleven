package com.motiveschina.erp.domain.material;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MaterialCategory {
    // --------------------- 基础业务分类 ---------------------
    RAW("RAW_MATERIAL", "未加工的基础物料，如钢材、塑料颗粒，需管理供应商批次"),
    SEM("SEMI_FINISHED_GOOD", "加工中需进一步处理的物料，如组装好的电路板，跟踪工序进度"),
    FIN("FINISHED_GOOD", "可直接销售的最终产品，如包装好的商品，关联销售订单追溯"),
    PAC("PACKAGING_MATERIAL", "用于产品包装的物料，如纸箱、标签，采购量与产量挂钩"),
    EQU("EQUIPMENT", "生产或运营用固定资产，如机床、叉车，管理资产折旧"),

    // --------------------- 扩展分类（可选）---------------------
    WIP("WORK_IN_PROGRESS", "生产线上未完工的物料，需跟踪工序状态"),
    REW("REWORK_MATERIAL", "需重新加工的不合格品，独立质量流程"),
    NSI("NON_STOCK_ITEM", "一次性消耗品，如螺丝、垫片，不计入常规库存"),
    HAZ("HAZARDOUS_MATERIAL", "需特殊存储的化学品，如强酸、易燃品，合规管控");

    private final String name;
    private final String description;

    /**
     * 通过业务编码（即枚举常量）获取枚举（支持数据库存储值反向解析）
     *
     * @param code 业务编码（如 "RAW"）
     * @return 对应的 MaterialCategory，不存在时返回 null
     */
    public static MaterialCategory valueOfByCode(String code) {
        for (MaterialCategory category : MaterialCategory.values()) {
            if (category.name().equals(code)) {
                return category;
            }
        }
        return null;
    }

    /**
     * 通过业务名称获取枚举（支持用户输入正向匹配）
     *
     * @param name 业务名称（如 "RAW_MATERIAL"）
     * @return 对应的 MaterialCategory，不存在时返回 null
     */
    public static MaterialCategory valueOfByName(String name) {
        for (MaterialCategory category : MaterialCategory.values()) {
            if (category.getName().equals(name)) {
                return category;
            }
        }
        return null;
    }
}
