package com.wx.lab.view.annotation;

import java.lang.annotation.*;

/**
 * @author wangxiao@ybm100.com
 * @date 2020-12-15 下午1:39
 * @projectname totipotent
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
@Documented
public @interface ReCreateTable {

    String baseTable() default "";

    
}
