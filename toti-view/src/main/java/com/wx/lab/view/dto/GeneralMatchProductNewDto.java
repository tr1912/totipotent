package com.wx.lab.view.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangxiao
 * @email wangxiao@ybm100.com
 * @date 2021-01-20 15:59
 * @projectname product-interface-web
 */
public class GeneralMatchProductNewDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 入参编码
     */
    private String oldBusinessCode;

    /**
     * 入参商品分类id
     */
    private Integer oldSpuCategory;

    /**
     * 入参通用名
     */
    private String oldGeneralName;

    /**
     * 入参批准文号
     */
    private String oldApprovalNo;


    /**
     * 入参生产厂家名称
     */
    private String oldManufacturerName;


    /**
     * 入参规格/型号
     */
    private String oldSpec;

    /**
     * 入参小包装条码
     */
    private String oldSmallPackageCode;

    /**
     * 商品编码
     */
    private String businessCode;

    /**
     * 原商品编码
     */
    private String originalProductCode;

    /**
     * 标准库id
     */
    private String productId;

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
     * 生产厂家id
     */
    private Integer manufacturerId;


    /**
     * 规格/型号
     */
    private String spec;

    /**
     * 小包装条码
     */
    private String smallPackageCode;

    /**
     * 参数校验结果类型0匹配成功1初始状态 2校验未用过3 匹配到多条  4未匹配到
     */
    private Integer checkCodeType = 1;
    /**
     * 参数校验结果类型0匹配成功 1初始状态 2校验未用过 3 匹配到多条  4未匹配到
     */
    private Integer checkSpecType = 1;

    /**
     * 两要素匹配状态 0匹配成功 1初始状态  2校验未用过  3 匹配到多条  4未匹配到
     */
    private Integer twoElementsType = 1;

    /**
     * 三要素匹配状态 0匹配成功 1初始状态  2校验未用过 3 匹配到多条 4未匹配到
     */
    private Integer threeElementsType = 1;

    /**
     * 最终状态  0匹配成功 1初始状态  2匹配失败
     */
    private Integer resultType = 1;

    /**
     * 匹配到的商品列表
     */
    private List<GeneralMatchProductNewDto> matchList;

    /**
     * 第一没匹配上  第二次转换规格后 的规格值
     */
    private String specValueTwo;

    public String getSpecValueTwo() {
        return specValueTwo;
    }

    public void setSpecValueTwo(String specValueTwo) {
        this.specValueTwo = specValueTwo;
    }

    public String getOldBusinessCode() {
        return oldBusinessCode;
    }

    public void setOldBusinessCode(String oldBusinessCode) {
        this.oldBusinessCode = oldBusinessCode;
    }

    public Integer getOldSpuCategory() {
        return oldSpuCategory;
    }

    public void setOldSpuCategory(Integer oldSpuCategory) {
        this.oldSpuCategory = oldSpuCategory;
    }

    public String getOldGeneralName() {
        return oldGeneralName;
    }

    public void setOldGeneralName(String oldGeneralName) {
        this.oldGeneralName = oldGeneralName;
    }

    public String getOldApprovalNo() {
        return oldApprovalNo;
    }

    public void setOldApprovalNo(String oldApprovalNo) {
        this.oldApprovalNo = oldApprovalNo;
    }

    public String getOldManufacturerName() {
        return oldManufacturerName;
    }

    public void setOldManufacturerName(String oldManufacturerName) {
        this.oldManufacturerName = oldManufacturerName;
    }

    public String getOldSpec() {
        return oldSpec;
    }

    public void setOldSpec(String oldSpec) {
        this.oldSpec = oldSpec;
    }

    public String getOldSmallPackageCode() {
        return oldSmallPackageCode;
    }

    public void setOldSmallPackageCode(String oldSmallPackageCode) {
        this.oldSmallPackageCode = oldSmallPackageCode;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getOriginalProductCode() {
        return originalProductCode;
    }

    public void setOriginalProductCode(String originalProductCode) {
        this.originalProductCode = originalProductCode;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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
        this.generalName = generalName;
    }

    public String getApprovalNo() {
        return approvalNo;
    }

    public void setApprovalNo(String approvalNo) {
        this.approvalNo = approvalNo;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public Integer getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Integer manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getSmallPackageCode() {
        return smallPackageCode;
    }

    public void setSmallPackageCode(String smallPackageCode) {
        this.smallPackageCode = smallPackageCode;
    }

    public Integer getCheckCodeType() {
        return checkCodeType;
    }

    public void setCheckCodeType(Integer checkCodeType) {
        this.checkCodeType = checkCodeType;
    }

    public Integer getCheckSpecType() {
        return checkSpecType;
    }

    public void setCheckSpecType(Integer checkSpecType) {
        this.checkSpecType = checkSpecType;
    }

    public Integer getTwoElementsType() {
        return twoElementsType;
    }

    public void setTwoElementsType(Integer twoElementsType) {
        this.twoElementsType = twoElementsType;
    }

    public Integer getThreeElementsType() {
        return threeElementsType;
    }

    public void setThreeElementsType(Integer threeElementsType) {
        this.threeElementsType = threeElementsType;
    }

    public Integer getResultType() {
        return resultType;
    }

    public void setResultType(Integer resultType) {
        this.resultType = resultType;
    }

    public List<GeneralMatchProductNewDto> getMatchList() {
        return matchList;
    }

    public void setMatchList(List<GeneralMatchProductNewDto> matchList) {
        this.matchList = matchList;
    }
}
