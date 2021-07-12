package com.wx.lab.design.patterns.decorator.product;

import lombok.Data;

/**
 * ��Ʒ������
 * @author wangxiao
 * @version 1.0
 * @created 01-7��-2021 9:45:54
 */
@Data
public class ProductReviewDTO extends ReviewInfoDTO {

	/**
	 * ��Ӫ���� 1��Ӫ 2 ����Ӫ
	 */
	private int businessTupe;
	/**
	 * �����Ʒ��Ϣ
	 */
	private ProductInfo productInfo;
	/**
	 * ��Ʒ���ͣ� 1 sau ��2 sku��3��spu
	 */
	private int productType;

}