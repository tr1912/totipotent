package com.wx.lab.design.patterns.decorator.product.review;

import com.wx.lab.design.patterns.decorator.product.ApprovalProcessEnum;
import com.wx.lab.design.patterns.decorator.product.PictureReviewDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author wangxiao
 * @email wangxiao@ybm100.com
 * @date 2021-07-01 0001 13:48
 * Project totipotent
 */
@Slf4j
@Component
public class ReviewPictureTask extends PictureBaseReview{

    @Override
    public ApprovalProcessEnum getType() {
        return ApprovalProcessEnum.PROCESS_PRODUCT_DEDUPLICATION;
    }

    @Override
    public boolean auditPass(PictureReviewDTO reviewDTO) {
        super.auditPass(reviewDTO);
        log.info("run picture task auditPass");
        return true;
    }

    @Override
    public boolean auditReject(PictureReviewDTO reviewDTO) {
        super.auditReject(reviewDTO);
        log.info("run picture task auditReject");
        return true;
    }

    @Override
    public boolean initialReview(PictureReviewDTO reviewDTO) {
        super.initialReview(reviewDTO);
        log.info("run picture task initialReview");
        return true;
    }

    @Override
    public boolean receiveReview(PictureReviewDTO reviewDTO) {
        super.receiveReview(reviewDTO);
        log.info("run picture task receiveReview");
        return true;
    }
}
