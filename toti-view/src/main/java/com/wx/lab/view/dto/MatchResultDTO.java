package com.wx.lab.view.dto;

import lombok.Builder;
import lombok.Data;

import java.util.regex.Matcher;

/**
 * @author wangxiao
 * @email wangxiao@ybm100.com
 * @date 2021-06-10 17:48
 * @packagename com.wx.lab.view.dto
 */
@Data
@Builder
public class MatchResultDTO {

    /**
     * 匹配条件
     */
    RegxPatternDTO pattern;

    /**
     * 匹配结果
     */
    Matcher matcher;
}
