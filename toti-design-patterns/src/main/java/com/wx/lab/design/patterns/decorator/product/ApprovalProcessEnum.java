package com.wx.lab.design.patterns.decorator.product;


import java.util.*;

/**
 * ApprovalProcessEnum
 *
 * @author wsx
 * @date
 * @description 审批流程 枚举
 */
public enum ApprovalProcessEnum {
    /**
     * 条形码修改
     */
    PROCESS_PACKAGE_BAR_CODE(-1, "条形码修改", 0),
    PROCESS_SINGLE_ADD(0, "商品新增", 0),
    PROCESS_SINGLE_MODIFY(1, "商品修改", 0),
    PROCESS_SINGLE_MERGE(2, "商品合并", 1),
    PROCESS_BATCH_DISABLE(3, "批量停用", 1),
    PROCESS_BATCH_EXTENSION(4, "批量扩展", 1),
    PROCESS_BATCH_MODIFY(5, "批量修改", 1),
    PROCESS_PRE_OPERATE_MODIFY(6, "预首营审核", 0),
    PROCESS_BATCH_ADD(7, "批量新增", 1),
    PROCESS_BATCH_EXTEND_GUIDANCE(8, "批量用药指导", 1),
    PROCESS_BATCH_MODIFY_PLUS(9, "数据梳理更新", 1),
    PROCESS_SINGLE_CANCEL_MERGE(10, "商品取消合并", 1),
    PROCESS_SINGLE_SHELVES(12, "商品上架", 0),
    PROCESS_SINGLE_CORRECT(13, "商品纠错", 0),
    PROCESS_BATCH_MODIFY_TAX(14, "批量修改税率", 1),
    PROCESS_PRODUCT_DEDUPLICATION(15, "商品去重", 1),
    PROCESS_SPU_MERGE(16, "移动商品", 1),
    PROCESS_BATCH_SPU_DISABLE(17, "spu停启用", 1);
    /**
     * id
     */
    private short id;

    /**
     * 名称
     */
    private String name;
    /**
     * 是否批量 0-非批量 1-批量
     */
    private Integer batch;

    ApprovalProcessEnum(Integer id, String name, Integer batch) {
        this.id = id.shortValue();
        this.name = name;
        this.batch = batch;
    }

    public short getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public static String getApprovalProcessEnumName(Short id) {
        if (null == id) {
            return "";
        }
        for (ApprovalProcessEnum enums : values()) {
            if (enums.getId() == id) {
                return enums.getName();
            }
        }
        return "";
    }

    public static Integer getApprovalProcessEnumBatch(Short id) {
        if (null == id) {
            return null;
        }
        for (ApprovalProcessEnum enums : values()) {
            if (enums.getId() == id) {
                return enums.getBatch();
            }
        }
        return null;
    }

    public static ApprovalProcessEnum getApprovalProcessEnum(Short id) {
        if (null == id) {
            return null;
        }
        for (ApprovalProcessEnum enums : values()) {
            if (enums.getId() == id) {
                return enums;
            }
        }
        return null;
    }

    public Integer getBatch() {
        return batch;
    }

    /**
     * 获得所有的枚举类型
     *
     * @return
     */
    public static List<Map<String, String>> getAllProcess() {
        List<Map<String, String>> result = new ArrayList<>();
        Arrays.stream(ApprovalProcessEnum.values()).forEach(value -> {
            Map<String, String> item = new HashMap<>();
            item.put("key", String.valueOf(value.getId()));
            item.put("value", value.getName());
            result.add(item);
        });
        return result;
    }
}
