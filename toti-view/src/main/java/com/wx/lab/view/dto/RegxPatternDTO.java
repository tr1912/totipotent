package com.wx.lab.view.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author wangxiao
 * @email wangxiao@ybm100.com
 * @date 2021-06-10 17:21
 * @packagename com.wx.lab.view.dto
 */
@Data
@Builder
public class RegxPatternDTO {

    /**
     * 规则index
     */
    private Integer index;

    /**
     * 正则表达式
     */
    private String pattern;

    /**
     * 规格el表达式
     */
    private String elExpression;

    /**
     * 包装单位el表达式
     */
    private String packageUnitElExpress;

}
