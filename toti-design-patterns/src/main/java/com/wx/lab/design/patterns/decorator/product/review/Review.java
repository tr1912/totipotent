package com.wx.lab.design.patterns.decorator.product.review;

import com.wx.lab.design.patterns.decorator.product.ApprovalProcessEnum;
import com.wx.lab.design.patterns.decorator.product.ReviewInfoDTO;

/**
 * @author wangxiao
 * @version 1.0
 * @created 01-7��-2021 9:58:53
 */
public interface Review<T extends ReviewInfoDTO> {

	ApprovalProcessEnum getType();

	/**
	 * ���ͨ��
	 *
	 * @param reviewDTO
	 */
	boolean auditPass(T reviewDTO);

	/**
	 * ��˲���
	 * 
	 * @param reviewDTO
	 */
	boolean auditReject(T reviewDTO);

	/**
	 * start act ��ʼ��
	 * 
	 * @param reviewDTO
	 */
	boolean initialReview(T reviewDTO);

	/**
	 * ��ȡ���
	 * 
	 * @param reviewDTO
	 */
	boolean receiveReview(T reviewDTO);

}