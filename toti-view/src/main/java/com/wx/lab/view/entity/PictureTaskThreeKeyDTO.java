package com.wx.lab.view.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;

/**
 * @author wangxiao
 * @email wangxiao@ybm100.com
 * @date 2021-04-26 14:36
 * @packagename com.xyy.me.product.core.dto.product.picture
 */
@Builder
@Data
public class PictureTaskThreeKeyDTO {

    private String applyCode;

    private String productCode;

    private Integer approvalProcess;

    @Override
    public String toString() {
        return "PictureTaskThreeKeyDTO{" +
                "applyCode='" + applyCode + '\'' +
                ", productCode='" + productCode + '\'' +
                ", approvalProcess=" + approvalProcess +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PictureTaskThreeKeyDTO that = (PictureTaskThreeKeyDTO) o;
        return Objects.equals(applyCode, that.applyCode) &&
                Objects.equals(productCode, that.productCode) &&
                Objects.equals(approvalProcess, that.approvalProcess);
    }

    @Override
    public int hashCode() {
        return Objects.hash(applyCode, productCode, approvalProcess);
    }
}
