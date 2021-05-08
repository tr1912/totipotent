package com.wx.lab.view.context;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author duyujie@ybm100.com
 * @Title: ProductContext
 * @Description:
 * @Company: ybm100.com
 * @Created on 2019/9/25 16:54
 * @ModifiedBy:
 * @Copyright: Copyright (c) 2019
 */
public class ProductContext extends BaseModel {
    private static final long serialVersionUID = -7822406474905730149L;

    /** 用户请求的服务ID，由入口产品所在环境分配 */
    private String requestId;

    /** traceId */
    private String traceId;


    // ----- 下面的属性主要为Thread共享使用，不需要把其中的属性进行网络传输---------
    /**
     * 存储环境数据
     */
    private transient Map<String, Object> context = null;

    /**
     *
     */
    public ProductContext() {
        requestId = UUID.randomUUID().toString().replace("-","");
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }


    // //////////////////动态属性操作方法////////////////////////
    /**
     * 向环境中放置数据
     *
     * @param key
     * @param value
     */
    public void put(String key, Object value) {
        if (null == context) {
            context = Collections.synchronizedMap(new HashMap<String, Object>());
        }
        context.put(key, value);
    }

    /**
     * 从环境中获取数据
     *
     * @param key
     * @return
     */
    public Object find(String key) {
        if (null == context) {
            context = Collections.synchronizedMap(new HashMap<String, Object>());
        }
        return context.get(key);
    }

    /**
     * 释放缓存中的数据
     */
    public void clean() {
        if (null != context) {
            context.clear();
        }
    }
}
