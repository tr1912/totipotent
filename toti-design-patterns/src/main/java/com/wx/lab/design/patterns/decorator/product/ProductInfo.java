package com.wx.lab.design.patterns.decorator.product;

import lombok.Data;

/**
 * ��Ʒ��Ϣ
 * @author wangxiao
 * @version 1.0
 * @created 01-7��-2021 9:45:53
 */
@Data
public class ProductInfo {

	/**
	 * ��Ʒid
	 */
	private int productId;
	/**
	 * ��Ʒ����
	 */
	private char productName;
	/**
	 * sau����
	 */
	private char sauCode;

}