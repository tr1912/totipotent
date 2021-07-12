import com.wx.lab.design.patterns.decorator.product.ApprovalProcessEnum;
import com.wx.lab.design.patterns.decorator.product.ProductReviewDTO;
import com.wx.lab.design.patterns.decorator.product.review.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author wangxiao
 * @email wangxiao@ybm100.com
 * @date 2021-07-01 0001 14:54
 * Project totipotent
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ReviewFactory.class,
        ReviewCreateProduct.class,
        ReviewPictureShootTask.class,
        ReviewPictureTask.class,
        ReviewProductPresent.class})
public class TestReview {
    private ReviewFactory<ProductReviewDTO> reviewFactory;

    @Autowired
    public void setReviewFactory(ReviewFactory<ProductReviewDTO> reviewFactory) {
        this.reviewFactory = reviewFactory;
    }

    @Test
    public void reviewCreateProduct(){
        Review<ProductReviewDTO> reviewHandler = reviewFactory.getReviewHandler(ApprovalProcessEnum.PROCESS_BATCH_ADD);
        ProductReviewDTO param = new ProductReviewDTO();
        reviewHandler.receiveReview(param);
        reviewHandler.initialReview(param);
        reviewHandler.auditPass(param);
        reviewHandler.auditReject(param);
    }
}
