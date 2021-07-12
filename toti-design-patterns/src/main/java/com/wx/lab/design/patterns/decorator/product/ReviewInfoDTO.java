package com.wx.lab.design.patterns.decorator.product;

import lombok.Data;

/**
 * ������
 * @author wangxiao
 * @version 1.0
 * @created 01-7��-2021 9:45:55
 */
@Data
public class ReviewInfoDTO {

	/**
	 * �����id
	 */
	private int reviewerId;
	/**
	 * ��˽�� 1 ͨ����2���أ�3��������
	 */
	private int reviewResult;
	/**
	 * ���ʱ��
	 */
	private long reviewTime;
	/**
	 * �������
	 */
	private int reviewType;

}