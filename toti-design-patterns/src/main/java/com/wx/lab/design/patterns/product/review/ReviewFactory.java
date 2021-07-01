package com.wx.lab.design.patterns.product.review;

import com.wx.lab.design.patterns.product.ApprovalProcessEnum;
import com.wx.lab.design.patterns.product.ReviewInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wangxiao
 * @email wangxiao@ybm100.com
 * @date 2021-07-01 0001 14:25
 * Project totipotent
 */
@Slf4j
@Component
public class ReviewFactory<T extends ReviewInfoDTO> {

    private final List<Review<T>> reviews;

    public ReviewFactory(List<Review<T>> reviews) {
        this.reviews = reviews;
    }

    public Review<T> getReviewHandler(ApprovalProcessEnum processEnum){
        return reviews.stream().filter(n->n.getType().equals(processEnum))
                .findAny()
                .orElseThrow(()->new RuntimeException(""));
    }

}
