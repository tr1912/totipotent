package com.wx.lab.view.context;

/**
 * @author duyujie@ybm100.com
 * @Title: ProductContextHolder
 * @Description: ProductContext
 * @Company: ybm100.com
 * @Created on 2019/9/25 16:58
 * @ModifiedBy:
 * @Copyright: Copyright (c) 2019
 */
public class ProductContextHolder {
    private static ThreadLocal<ProductContext> context = new ThreadLocal<ProductContext>() {
        // 初始化值
        @Override
        public ProductContext initialValue() {
            return new ProductContext();
        }
    };

    public static final String WEB_CNTXT_RESULT_CONSTANT = "$$WEB_CNTXT_RESULT_CONSTANT";
    /**
     * 清空
     */
    public static void clean() {
        if (null != context.get()) {
            context.get().clean();
            context.set(null);
        }
    }

    public static ProductContext getProductContext() {
        if (null == context.get()) {
            context.set(new ProductContext());
        }
        return context.get();
    }

    /**
     * 强制覆盖现有的产品环境
     *
     * @param productContext
     */
    public static void setProductContext(ProductContext productContext) {
        context.set(productContext);
    }

    /**
     * 获取状态
     *
     * @return
     */
    public static boolean isWebInitContext() {
        boolean isWeb = null == getProductContext().find(WEB_CNTXT_RESULT_CONSTANT) ? false : (Boolean) getProductContext().find(WEB_CNTXT_RESULT_CONSTANT);
        return isWeb;
    }
}
