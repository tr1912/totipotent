package com.wx.lab.view.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangyanlong
 * @date 2020/6/8 16:36
 * @description
 */
public class GeneralMatchProductVo implements Serializable {

    private static final long serialVersionUID = -4170878237647676475L;


    /**
     * 入参五要素集合
     */
    private List<GeneralMatchProduct> productList;

    /**
     * 调用链标识
     */
    private String traceId;

    /**
     * 来源（1荷叶健康，2智慧脸商城，3POP）
     */
    private Integer source;


    public List<GeneralMatchProduct> getProductList() {
        return productList;
    }

    public void setProductList(List<GeneralMatchProduct> productList) {
        this.productList = productList;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }
}
