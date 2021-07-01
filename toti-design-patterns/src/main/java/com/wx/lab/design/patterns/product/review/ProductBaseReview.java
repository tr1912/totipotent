package com.wx.lab.design.patterns.product.review;

import com.wx.lab.design.patterns.product.ApprovalProcessEnum;
import com.wx.lab.design.patterns.product.ProductReviewDTO;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wangxiao
 * @version 1.0
 * @created 01-7��-2021 9:58:51
 */
@Slf4j
public abstract class ProductBaseReview implements Review<ProductReviewDTO> {

	@Override
	public abstract ApprovalProcessEnum getType();

	/**
	 * ���ͨ��
	 *
	 * @param reviewDTO
	 */
	public boolean auditPass(ProductReviewDTO reviewDTO){
		log.info("run product base auditPass");
		return true;
	}

	/**
	 * ��˲���
	 * 
	 * @param reviewDTO
	 */
	public boolean auditReject(ProductReviewDTO reviewDTO){
		log.info("run product base auditReject");
		return true;
	}

	/**
	 * start act ��ʼ��
	 * 
	 * @param reviewDTO
	 */
	public boolean initialReview(ProductReviewDTO reviewDTO){
		log.info("run product base initialReview");
		return true;
	}

	/**
	 * ��ȡ���
	 * 
	 * @param reviewDTO
	 */
	public boolean receiveReview(ProductReviewDTO reviewDTO){
		log.info("run product base receiveReview");
		return true;
	}

}