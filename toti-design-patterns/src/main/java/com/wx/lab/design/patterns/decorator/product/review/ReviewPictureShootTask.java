package com.wx.lab.design.patterns.decorator.product.review;

import com.wx.lab.design.patterns.decorator.product.ApprovalProcessEnum;
import com.wx.lab.design.patterns.decorator.product.PictureReviewDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author wangxiao
 * @email wangxiao@ybm100.com
 * @date 2021-07-01 0001 13:51
 * Project totipotent
 */
@Slf4j
@Component
public class ReviewPictureShootTask extends PictureBaseReview{

    @Override
    public ApprovalProcessEnum getType() {
        return ApprovalProcessEnum.PROCESS_SPU_MERGE;
    }

    @Override
    public boolean auditPass(PictureReviewDTO reviewDTO) {
        super.auditPass(reviewDTO);
        log.info("run picture shoot task auditPass");
        return false;
    }

    @Override
    public boolean auditReject(PictureReviewDTO reviewDTO) {
        super.auditReject(reviewDTO);
        log.info("run picture shoot task auditReject");
        return false;
    }

    @Override
    public boolean initialReview(PictureReviewDTO reviewDTO) {
        super.initialReview(reviewDTO);
        log.info("run picture shoot task initialReview");
        return false;
    }

    @Override
    public boolean receiveReview(PictureReviewDTO reviewDTO) {
        super.receiveReview(reviewDTO);
        log.info("run picture shoot task receiveReview");
        return false;
    }
}
