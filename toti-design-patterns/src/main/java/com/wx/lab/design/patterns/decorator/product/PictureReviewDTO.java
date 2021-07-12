package com.wx.lab.design.patterns.decorator.product;

import lombok.Data;

/**
 * ͼƬ������
 * @author wangxiao
 * @version 1.0
 * @created 01-7��-2021 9:45:52
 */
@Data
public class PictureReviewDTO extends ReviewInfoDTO {

	/**
	 * ��Ӫ״̬��1��Ӫ��2����Ӫ
	 */
	private int businessType;
	/**
	 * ��Դ
	 */
	private int source;

}