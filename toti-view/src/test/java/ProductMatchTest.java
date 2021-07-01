import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.wx.lab.view.config.ExcelListener;
import com.wx.lab.view.dto.GeneralMatchProduct;
import com.wx.lab.view.dto.GeneralMatchProductVo;
import com.wx.lab.view.dto.ProductMatchSourceDTO;
import com.wx.lab.view.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author wangxiao
 * @email wangxiao@ybm100.com
 * @date 2021-07-01 0001 18:30
 * Project totipotent
 */
@Slf4j
public class ProductMatchTest {

    private List<ProductMatchSourceDTO> importExcel() throws FileNotFoundException {
        String fileName = "D:\\线上新品上报审核中数据.xlsx";
        File file = new File(fileName);
        InputStream in = new FileInputStream(file);
        List<ProductMatchSourceDTO> list = new ArrayList<>();
        EasyExcelFactory.read(in, ProductMatchSourceDTO.class, new ExcelListener<ProductMatchSourceDTO>(list::addAll)).sheet().doRead();
        return list;
    }

    @Test
    public void testUrlMatch() throws FileNotFoundException {
        // 源数据
        List<ProductMatchSourceDTO> matchSources = importExcel();
        List<GeneralMatchProduct> products = matchSources.stream()
                .map(m->{
                    GeneralMatchProduct matchProduct = new GeneralMatchProduct();
                    BeanUtils.copyProperties(m, matchProduct);
                    matchProduct.setBusinessCode("-");
                    if (StringUtils.isEmpty(matchProduct.getSmallPackageCode())){
                        matchProduct.setSmallPackageCode("0");
                    }
                    if (matchProduct.getSpuCategory() == null){
                        matchProduct.setSpuCategory(1);
                    }
                    if (StringUtils.isEmpty(matchProduct.getSpec())){
                        matchProduct.setSpec("-");
                    }
                    if (StringUtils.isEmpty(matchProduct.getManufacturerName())){
                        matchProduct.setManufacturerName("-");
                    }
                    if (StringUtils.isEmpty(matchProduct.getGeneralName())){
                        matchProduct.setGeneralName("-");
                    }
                    return matchProduct;
                })
                .collect(Collectors.toList());
        List<List<GeneralMatchProduct>> lists = Lists.partition(products, 200);
        AtomicInteger i = new AtomicInteger(1);
        lists.forEach(m->{
            GeneralMatchProductVo productVo = new GeneralMatchProductVo();
            productVo.setTraceId("wx-728832095c-2716bea90c");
            productVo.setSource(12);
            productVo.setProductList(m);
            HttpClientUtil client = new HttpClientUtil();
            String url = "https://interface.ybm100.com/me/newsku/getGeneralMatchProduct";
            String jsonParams = JSON.toJSONString(productVo);
            try {
                long start = System.currentTimeMillis();
                client.doPost(url, jsonParams,new HashMap<>());
                i.getAndIncrement();
                log.info("当前第{}个循环，执行时间：{}",i.get(), (System.currentTimeMillis() - start));
            } catch (IOException e) {
                log.error("io错误", e);
            } catch (Exception e) {
                log.error("错误", e);
            }
        });
    }
}
