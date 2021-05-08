package com.wx.lab.view.context;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * @author duyujie@ybm100.com
 * @Title: MdcHolder
 * @Description:
 * @Company: ybm100.com
 * @Created on 2019/9/25 13:42
 * @ModifiedBy:
 * @Copyright: Copyright (c) 2019
 */
public class MdcHolder {
    private static Logger log = LoggerFactory.getLogger(MdcHolder.class);

    public static final String MDC_REQUEST_ID_KEY = "MDC_REQUEST_ID";

    public static final String MDC_SESSION_KEY = "MDC_TRACE_ID";

    public static final String MDC_CLIENT_IP_KEY = "MDC_CLIENT_IP";

    public static final String MDC_SERVER_IP_KEY = "MDC_SERVER_IP";

    private static ThreadLocal<Long> MDC_COUNT = new ThreadLocal<Long>() {
        // 初始化值
        @Override
        public Long initialValue() {
            return 0L;
        }
    };

    /**
     * 初始化
     *
     * @param productContext
     */
    public static void init(ProductContext productContext) {
        try {
            MDC.put(MDC_REQUEST_ID_KEY, productContext.getRequestId());
            if (StringUtils.isNotBlank(productContext.getTraceId())) {
                MDC.put(MDC_SESSION_KEY, productContext.getTraceId());
            }
        } catch (Exception e) {
            log.warn("initMDC:" + e.getMessage());
        } finally {
            MDC_COUNT.set(MDC_COUNT.get() + 1);
        }
    }

    public static void clean() {
        Long c = MDC_COUNT.get();
        if (null != c && c.longValue() > 1) {
            MDC_COUNT.set(MDC_COUNT.get() - 1L);
        } else {
            MDC_COUNT.set(0L);
            MDC.clear();
        }
    }
}
