package com.wx.lab.view.service;

/**
 * @author wangxiao
 * @email wangxiao@ybm100.com
 * @date 2021-04-13 13:25
 * @packagename com.xyy.me.product.core.api
 */
public interface QLExpressApi {

    /**
     * 字符串匹配返回方法
     *
     * @param param
     * @param expressString
     * @return
     */
    Object startExecute(Object param, String expressString);

    /**
     * 返回字符串的 ql执行方法
     *
     * @param param
     * @param expressString
     * @return
     */
    String getQlString(Object param, String expressString);
}
