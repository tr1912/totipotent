package com.wx.lab.design.patterns.decorator.product.review;

import com.wx.lab.design.patterns.decorator.product.ApprovalProcessEnum;
import com.wx.lab.design.patterns.decorator.product.ProductReviewDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author wangxiao
 * @version 1.0
 * @created 01-7��-2021 9:58:54
 */
@Slf4j
@Component
public class ReviewCreateProduct extends ProductBaseReview {

	@Override
	public ApprovalProcessEnum getType() {
		return ApprovalProcessEnum.PROCESS_BATCH_ADD;
	}

	/**
	 * ���ͨ��
	 *
	 * @param reviewDTO
	 */
	public boolean auditPass(ProductReviewDTO reviewDTO){
		super.auditPass(reviewDTO);
		log.info("run create product auditPass");
		return true;
	}

	/**
	 * ��˲���
	 * 
	 * @param reviewDTO
	 */
	public boolean auditReject(ProductReviewDTO reviewDTO){
		super.auditReject(reviewDTO);
		log.info("run create product auditReject");
		return true;
	}

	/**
	 * start act ��ʼ��
	 * 
	 * @param reviewDTO
	 */
	public boolean initialReview(ProductReviewDTO reviewDTO){
		super.initialReview(reviewDTO);
		log.info("run create product initialReview");
		return true;
	}

	/**
	 * ��ȡ���
	 * 
	 * @param reviewDTO
	 */
	public boolean receiveReview(ProductReviewDTO reviewDTO){
		super.receiveReview(reviewDTO);
		log.info("run create product receiveReview");
		return true;
	}

}