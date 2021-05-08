package com.wx.lab.view.annotation;

import java.lang.annotation.*;

/**
 * @author wangxiao
 * @email wangxiao@ybm100.com
 * @date 2021-04-21 14:56
 * @packagename com.wx.lab.view.annotation
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
@Documented
public @interface ServiceInvocationLogger {
}
