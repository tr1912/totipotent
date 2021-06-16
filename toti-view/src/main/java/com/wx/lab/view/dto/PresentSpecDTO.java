package com.wx.lab.view.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author wangxiao
 * @email wangxiao@ybm100.com
 * @date 2021-06-15 0015 15:45
 * Project totipotent
 */
@Data
public class PresentSpecDTO {

    @ExcelProperty(value = "新品上报id")
    private Integer presentId;

    @ExcelProperty(value = "标准库id")
    private Integer productId;

    @ExcelProperty(value = "商品大类")
    private String spuCategory;

    @ExcelProperty(value = "m商品大类")
    private String middleCategory;

    @ExcelProperty(value = "通用名")
    private String generalName;

    @ExcelProperty(value = "m通用名")
    private String middleGeneralName;

    @ExcelProperty(value = "m生产厂家")
    private String manufacture;

    @ExcelProperty(value = "m生产厂家")
    private String middleManufacture;

    @ExcelProperty(value = "批准文号")
    private String approvalNo;

    @ExcelProperty(value = "m批准文号")
    private String middleApprovalNo;

    @ExcelProperty(value = "规格")
    private String spec;

    @ExcelProperty(value = "m规格")
    private String middleSpec;

    @ExcelProperty(value = "新规格")
    private String matchSpec;

    @ExcelProperty(value = "新m规格")
    private String matchMiddleSpec;

    @ExcelProperty(value = "新包装单位")
    private String matchPackageUnit;

    @ExcelProperty(value = "是否匹配成功")
    private Boolean isMatch = false;
}
