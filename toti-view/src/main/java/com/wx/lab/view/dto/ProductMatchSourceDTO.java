package com.wx.lab.view.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author wangxiao
 * @email wangxiao@ybm100.com
 * @date 2021-07-01 0001 18:31
 * Project totipotent
 */
@Data
public class ProductMatchSourceDTO {

    @ExcelProperty(value = "唯一标识")
    private String uniqueCode;

    @ExcelProperty(value = "商品大类")
    private Integer spuCategory;

    @ExcelProperty(value = "通用名")
    private String generalName;

    @ExcelProperty(value = "批准文号")
    private String approvalNo;

    @ExcelProperty(value = "生产厂家")
    private String manufacturerName;

    @ExcelProperty(value = "小包装条码")
    private String smallPackageCode;

    @ExcelProperty(value = "规格")
    private String spec;

    @ExcelProperty(value = "标准库id")
    private String productId;

}
