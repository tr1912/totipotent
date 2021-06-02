import com.wx.lab.view.TotiViewApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author wangxiao
 * @email wangxiao@ybm100.com
 * @date 2021-06-02 13:26
 * @packagename PACKAGE_NAME
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotiViewApplication.class)
public class BiteLogicOperateTest {

    @Test
    public void testAnd(){
        int oneAndTwo = 1 & 2;
        log.info(oneAndTwo + "");
        int oneAndOne = 1 & 1;
        log.info(oneAndOne + "");
        int oneAndThree = 1 & 3;
        log.info(oneAndThree + "");
        int oneAndFour = 1 & 4;
        log.info(oneAndFour + "");
    }

    @Test
    public void testGetInt(){
        log.info(getInt() + "");
    }

    private int getInt(){
        int a = 1;
        try {
            return ++a;
        }catch (ArithmeticException e){

        }finally {
            ++a;
        }
        return a;
    }
}
