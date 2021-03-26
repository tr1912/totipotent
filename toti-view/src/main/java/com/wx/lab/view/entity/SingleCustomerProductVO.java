package com.wx.lab.view.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author wangxiao
 * @email wangxiao@ybm100.com
 * @date 2021-03-03 15:10
 * @packagename com.xyy.me.product.core.excel
 */
@Data
public class SingleCustomerProductVO {

    @ExcelProperty(value = "条码", index = 0)
    private String smallPackageCode;

    @ExcelProperty(value = "来源", index = 1)
    private Integer source;

    @ExcelProperty(value = "批文", index = 2)
    private String approvalNo;

    @ExcelProperty(value = "通用名", index = 3)
    private String generalName;

    @ExcelProperty(value = "规格", index = 4)
    private String spec;

    @ExcelProperty(value = "生产厂家", index = 5)
    private String manufacturerName;

    @ExcelProperty(value = "药店编码", index = 6)
    private String customerCode;

    @ExcelProperty(value = "药店商品编码", index = 7)
    private String customerProductCode;

    @ExcelProperty(value = "省份", index = 8)
    private String province;
}