package com.wx.lab.view.config;

import java.util.List;

/**
 * @author ：Nickels
 * @date ：2020/7/27
 * @desc ：
 */
@FunctionalInterface
public interface ExcelConsumer<E> {

    void excute(List<E> e);
}