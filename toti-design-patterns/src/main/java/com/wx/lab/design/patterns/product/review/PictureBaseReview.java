package com.wx.lab.design.patterns.product.review;

import com.wx.lab.design.patterns.product.ApprovalProcessEnum;
import com.wx.lab.design.patterns.product.PictureReviewDTO;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wangxiao
 * @version 1.0
 * @created 01-7��-2021 9:58:51
 */
@Slf4j
public abstract class PictureBaseReview implements Review<PictureReviewDTO> {

	@Override
	public abstract ApprovalProcessEnum getType();

	/**
	 * ���ͨ��
	 *
	 * @param reviewDTO
	 */
	public boolean auditPass(PictureReviewDTO reviewDTO){
		log.info("run picture base auditPass");
		return true;
	}

	/**
	 * ��˲���
	 * 
	 * @param reviewDTO
	 */
	public boolean auditReject(PictureReviewDTO reviewDTO){
		log.info("run picture base auditReject");
		return true;
	}

	/**
	 * start act ��ʼ��
	 * 
	 * @param reviewDTO
	 */
	public boolean initialReview(PictureReviewDTO reviewDTO){
		log.info("run picture base initialReview");
		return true;
	}

	/**
	 * ��ȡ���
	 * 
	 * @param reviewDTO
	 */
	public boolean receiveReview(PictureReviewDTO reviewDTO){
		log.info("run picture base receiveReview");
		return true;
	}

}