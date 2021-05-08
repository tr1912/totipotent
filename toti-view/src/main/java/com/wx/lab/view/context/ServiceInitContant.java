package com.wx.lab.view.context;

/**
 * @author duyujie@ybm100.com
 * @Title: ServiceInitContant
 * @Description:
 * @Company: ybm100.com
 * @Created on 2019/9/25 11:17
 * @ModifiedBy:
 * @Copyright: Copyright (c) 2019
 */
public  class ServiceInitContant {
    public static ThreadLocal<Long> SERVICE_INIT_COUNT = new ThreadLocal<Long>() {
        // 初始化值
        @Override
        public Long initialValue() {
            return 0L;
        }
    };
}
