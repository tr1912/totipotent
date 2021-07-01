package com.wx.lab.view.dto;

import java.io.Serializable;

/**
 * @author wangyanlong
 * @date 2020/6/8 16:36
 * @description
 */
public class GeneralMatchProduct implements Serializable {

    private static final long serialVersionUID = -4170878237647676475L;

    /**
     * 商品编码
     */
    private String businessCode;

    /**
     * 商品分类id
     */
    private Integer spuCategory;

    /**
     * 通用名
     */
    private String generalName;

    /**
     * 批准文号
     */
    private String approvalNo;


    /**
     * 生产厂家名称
     */
    private String manufacturerName;


    /**
     * 规格/型号
     */
    private String spec;

    /**
     * 小包装条码
     */
    private String smallPackageCode;

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }
    public Integer getSpuCategory() {
        return spuCategory;
    }

    public void setSpuCategory(Integer spuCategory) {
        this.spuCategory = spuCategory;
    }

    public String getGeneralName() {
        return generalName;
    }

    public void setGeneralName(String generalName) {
        this.generalName = generalName == null ? null : generalName.trim().replace(" ","");
    }

    public String getApprovalNo() {
        return approvalNo;
    }

    public void setApprovalNo(String approvalNo) {
        this.approvalNo = approvalNo == null ? null : approvalNo.trim().replace(" ","");
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName == null ? null : manufacturerName.trim().replace(" ","");
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    public String getSmallPackageCode() {
        return smallPackageCode;
    }

    public void setSmallPackageCode(String smallPackageCode) {
        this.smallPackageCode = smallPackageCode == null ? null : smallPackageCode.trim().replace(" ","");
    }
}
