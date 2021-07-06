import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.wx.lab.view.config.ExcelListener;
import com.wx.lab.view.dto.*;
import com.wx.lab.view.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
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

    // 定长线程池
    private ThreadFactory contentThreadFactory = new ThreadFactoryBuilder().setNameFormat("service-match-pool-%d").build();
    protected ExecutorService contentExecutor = new ThreadPoolExecutor(3, 16, 7000, TimeUnit.SECONDS, new LinkedBlockingQueue<>(200*10000), contentThreadFactory, new ThreadPoolExecutor.AbortPolicy());


    private List<ProductMatchSourceDTO> importExcel(String fileName) throws FileNotFoundException {

        File file = new File(fileName);
        InputStream in = new FileInputStream(file);
        List<ProductMatchSourceDTO> list = new ArrayList<>();
        EasyExcelFactory.read(in, ProductMatchSourceDTO.class, new ExcelListener<ProductMatchSourceDTO>(list::addAll)).sheet().doRead();
        return list;
    }

    @Test
    public void testUrlMatch() throws FileNotFoundException {
        String fileName = "D:\\线上新品上报审核中数据.xlsx";
        // 源数据
        List<ProductMatchSourceDTO> matchSources = importExcel(fileName);
        if (CollectionUtils.isEmpty(matchSources)){
            return;
        }
        AtomicInteger i = new AtomicInteger(1);
        for (ProductMatchSourceDTO matchSource : matchSources) {
            GeneralMatchProduct matchProduct = new GeneralMatchProduct();
            BeanUtils.copyProperties(matchSource, matchProduct);
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
            GeneralMatchProductVo productVo = new GeneralMatchProductVo();
            productVo.setTraceId("wx-728832095c-2716bea90c");
            productVo.setSource(13);
            productVo.setProductList(Collections.singletonList(matchProduct));
            HttpClientUtil client = new HttpClientUtil();
            String url = "https://interface-new.stage.ybm100.com/me/newsku/getGeneralMatchProduct";
            String jsonParams = JSON.toJSONString(productVo);
            try {
                long start = System.currentTimeMillis();
                String res = client.doPost(url, jsonParams, new HashMap<>());
                ResponseInfo<JSONArray> responseInfo = JSON.parseObject(res, ResponseInfo.class);
                if (responseInfo.isSuccess()){
                    List<GeneralMatchProductNewDto> dataList = JSON.parseArray(JSON.toJSONString(responseInfo.getData()),GeneralMatchProductNewDto.class);
                    if (!CollectionUtils.isEmpty(dataList)){
                        GeneralMatchProductNewDto generalMatchProductNewDto = dataList.get(0);
                        if (generalMatchProductNewDto.getResultType()!=null && generalMatchProductNewDto.getResultType() == 0){
                            matchSource.setProductId(generalMatchProductNewDto.getProductId());
                        }
                    }
                }
                log.info("当前第{}个循环，执行时间：{}",i.getAndIncrement(), (System.currentTimeMillis() - start));
            } catch (IOException e) {
                log.error("io错误", e);
            } catch (Exception e) {
                log.error("错误", e);
            }
        }
        List<ProductMatchSourceDTO> resProductSource = matchSources.stream().filter(n->!StringUtils.isEmpty(n.getProductId())).collect(Collectors.toList());
        String outFileName = "D:\\线上新品上报审核中数据-匹配结果.xlsx";
        EasyExcelFactory.write(outFileName, ProductMatchSourceDTO.class).sheet("Sheet1").doWrite(resProductSource);
    }

    @Test
    public void rejectPresent() throws FileNotFoundException {
        String fileName = "D:\\线上新品上报审核中数据-匹配结果.xlsx";
        // 源数据
        List<ProductMatchSourceDTO> matchSources = importExcel(fileName);
        if (CollectionUtils.isEmpty(matchSources)){
            return;
        }
        AtomicInteger i = new AtomicInteger(1);
        for (ProductMatchSourceDTO matchSource : matchSources) {
            Map<String,Object> param = new HashMap<>();
            param.put("uniqueCode", matchSource.getUniqueCode());
            param.put("statusCode", 2);
            param.put("statusMsg", "商品已存在，提报的商品已有标准库信息——标准库ID");
            param.put("productId", matchSource.getProductId());
            HttpClientUtil client = new HttpClientUtil();
            String url = "http://meproduct.ybm100.com//api/present/update/reject";
            // String url = "http://localhost:8081/api/present/update/reject";
            Map<String, String> headers = new HashMap<>();
            headers.put("Cookie","sid=1720c1d6-35c2-4a8c-96de-c3499a48aac8");
            String jsonParams = JSON.toJSONString(param);
            try {
                long start = System.currentTimeMillis();
                String res = client.doPost(url, param, headers, 10000);
                log.info("http调用结果：{}", res);
                log.info("当前第{}个循环，执行时间：{}",i.getAndIncrement(), (System.currentTimeMillis() - start));
            } catch (IOException e) {
                log.error("io错误", e);
            } catch (Exception e) {
                log.error("错误", e);
            }
        }
    }
}
