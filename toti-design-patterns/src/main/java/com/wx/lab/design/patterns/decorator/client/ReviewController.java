package com.wx.lab.design.patterns.decorator.client;

import com.wx.lab.design.patterns.decorator.product.ApprovalProcessEnum;
import com.wx.lab.design.patterns.decorator.product.ProductReviewDTO;
import com.wx.lab.design.patterns.decorator.product.review.Review;
import com.wx.lab.design.patterns.decorator.product.review.ReviewFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangxiao
 * @email wangxiao@ybm100.com
 * @date 2021-07-01 0001 14:37
 * Project totipotent
 */
@RestController("/api/review")
public class ReviewController {

    private ReviewFactory<ProductReviewDTO> reviewFactory;

    @Autowired
    public void setReviewFactory(ReviewFactory<ProductReviewDTO> reviewFactory) {
        this.reviewFactory = reviewFactory;
    }

    @GetMapping("/reviewCreateProduct")
    public String reviewCreateProduct(){
        Review<ProductReviewDTO> reviewHandler = reviewFactory.getReviewHandler(ApprovalProcessEnum.PROCESS_SINGLE_ADD);
        ProductReviewDTO param = new ProductReviewDTO();
        reviewHandler.receiveReview(param);
        reviewHandler.initialReview(param);
        reviewHandler.auditPass(param);
        reviewHandler.auditReject(param);
        return "123";
    }
}
