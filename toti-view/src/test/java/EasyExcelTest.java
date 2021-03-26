import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.wx.lab.view.config.ExcelListener;
import com.wx.lab.view.entity.SingleCustomerProductVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wangxiao
 * @email wangxiao@ybm100.com
 * @date 2021-03-03 15:37
 * @packagename PACKAGE_NAME
 */
@Slf4j
@RunWith(SpringRunner.class)
public class EasyExcelTest {


    /**
     * 最简单的读
     * <p>1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link DemoDataListener}
     * <p>3. 直接读即可
     */
    @Test
    public void simpleRead() {
        String fileName = "D:\\客户商品导入模板.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, SingleCustomerProductVO.class, new ExcelListener<SingleCustomerProductVO>(p->{
            System.out.println(p.size());
        })).sheet().doRead();
    }

    @Test
    public void getExcelInputStream(){
        InputStream inputStream = null;
        // 这里 需要指定写用哪个class去写
        ExcelWriter excelWriter = null;
        try {
            //临时缓冲区
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            EasyExcel.write(out, SingleCustomerProductVO.class).sheet("Sheet1").doWrite(data());
            inputStream = new ByteArrayInputStream(out.toByteArray());
            System.out.println(inputStream);
        }catch (Exception e){
            log.error("123", e);
        }
    }


    private List<SingleCustomerProductVO> data() {
        List<SingleCustomerProductVO> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            SingleCustomerProductVO data = new SingleCustomerProductVO();
            data.setSmallPackageCode(String.valueOf(i));
            data.setSource(i);
            data.setApprovalNo(String.valueOf(i));
            data.setGeneralName(String.valueOf(i));
            data.setSpec(String.valueOf(i));
            data.setManufacturerName(String.valueOf(i));
            data.setCustomerCode(String.valueOf(i));
            data.setCustomerProductCode(String.valueOf(i));
            data.setProvince(String.valueOf(i));
        }
        return list;
    }
}
