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
     * 基数位置
     */
    private Integer baseNum;

    /**
     * 倍率位置
     */
    private Integer ratioNum;

    /**
     * 是否拼接最后一个字符
     */
    private boolean joinLast;
}
