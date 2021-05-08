package com.wx.lab.view.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wx.lab.view.context.MdcHolder;
import com.wx.lab.view.context.ProductContext;
import com.wx.lab.view.context.ProductContextHolder;
import com.wx.lab.view.context.ServiceInitContant;
import com.wx.lab.view.enums.SegmentsEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * @author wangxiao
 * @email wangxiao@ybm100.com
 * @date 2021-04-21 15:41
 * @packagename com.wx.lab.view.filter
 */
@Slf4j
@Aspect
@Order(2)
@Component
public class ServiceLoggerAspect {

    private static final int SPLIT_INTERCEPT_LENGTH = 1000;

    @Around(value = "@annotation(com.wx.lab.view.annotation.ServiceInvocationLogger)")
    public Object aroundRunning(ProceedingJoinPoint point) throws Throwable {
        Signature signature =  point.getSignature();//方法签名
        Method methodSignart = ((MethodSignature) point.getSignature()).getMethod();
        try {
            String methodName = methodSignart.getName();
            ServiceInitContant.SERVICE_INIT_COUNT.set(ServiceInitContant.SERVICE_INIT_COUNT.get() + 1);
            log.info("当前执行的方法名：{}", methodName);
            pullContext(methodSignart);
            MdcHolder.init(ProductContextHolder.getProductContext());
            return point.proceed();
        }catch (Exception e){
            log.error("切面执行出现问题！", e);
            return null;
        }finally {
            try {
                Long c = ServiceInitContant.SERVICE_INIT_COUNT.get();
                if (null != c && c.longValue() > 1) {
                    log.info("当前初始化service总数：{}", c);
                    ServiceInitContant.SERVICE_INIT_COUNT.set(ServiceInitContant.SERVICE_INIT_COUNT.get() - 1L);
                } else {
                    ServiceInitContant.SERVICE_INIT_COUNT.set(0L);
                    ProductContextHolder.clean();
                }
                MdcHolder.clean();
            } catch (Exception e) {
                throw e;
            }
        }
    }

    /**
     * 拉取环境上下文数据
     */
    private void pullContext(Method methodSignart) {
        String productContextInfo = "{\"requestId\":\"123456789\",\"traceId\":\"POP_231244D233241\"}";
        if (StringUtils.isNotBlank(productContextInfo)) {
            ProductContextHolder.setProductContext(JSON.parseObject(productContextInfo, ProductContext.class));
        } else if (log.isDebugEnabled()) {
            log.debug("分布式 productContext传递失败");
        }
        if (StringUtils.isBlank(productContextInfo)) {
            ProductContext productContext = ProductContextHolder.getProductContext();
            if (StringUtils.isBlank(productContext.getTraceId())) {
                productContext.setTraceId(UUID.randomUUID().toString().replace("-", ""));
            }
            ProductContextHolder.setProductContext(productContext);
        }
    }

    /**
     * 方法打印日志
     *
     * @param interFace       接口名
     * @param methodName      方法名
     * @param isInvokeSuccess 是否成功
     * @param stopWatch       消耗时间
     * @param param           入参
     * @param result          返回值(打印1000字符)
     * @param e               异常信息
     * @return
     */
    private String getLoggerPrint(Class interFace, String methodName, boolean isInvokeSuccess, StopWatch stopWatch, Object param, Object result, Throwable e) {
        stopWatch.split();
        StringBuffer sb = new StringBuffer();
        /**
         * 业务信息 [(类名,方法名,业务执行结果,消耗时间)(入参数据)(返回结果数据)(异常信息)]
         */
        long time = stopWatch.getSplitTime();
        sb.append("[")
                .append("(")
                .append("LOCAL method:")
                .append(interFace)
                .append(".")
                .append(methodName)
                .append(",")
                .append(isInvokeSuccess ? "Y" : "N")
                .append(",")
                .append(time)
                .append("ms,")
                .append("Segments")
                .append(timeToSegments(time))
                .append(")")
                .append("(");
        String paramJson = param == null ? null
                : JSONObject.toJSONString(param);
        if (paramJson != null) {
            if (paramJson.length() <= SPLIT_INTERCEPT_LENGTH) {
                sb.append(paramJson);
            } else {
                sb.append(paramJson.substring(0, SPLIT_INTERCEPT_LENGTH - 1));
                sb.append("...");
            }
        } else {
            sb.append("-");
        }
        sb.append(")").append("(");
        //String resultJson = JSONObject.toJSONString(result);
        /*if (resultJson != null) {
            if (resultJson.length() <= SPLIT_INTERCEPT_LENGTH) {
                sb.append(resultJson);
            } else {
                sb.append(resultJson.substring(0, SPLIT_INTERCEPT_LENGTH - 1));
                sb.append("...");
            }
        } else {
            sb.append("-");
        }*/
        sb.append(")").append("(")
                .append(e != null ? e : "-")
                .append(")").append("]");
        return sb.toString();
    }

    /**
     * 耗时分段记录
     * @param time
     * @return
     */
    private String timeToSegments(long time){
        String segments = "";
        if(time < 100){
            segments = SegmentsEnum.TIMES_0.getText();
        }else if(time >= 100 && time < 1000){
            segments = SegmentsEnum.TIMES_100.getText();
        }else if(time >= 1000 && time < 2500){
            segments = SegmentsEnum.TIMES_1000.getText();
        }else if(time >= 2500 && time < 5000){
            segments = SegmentsEnum.TIMES_2500.getText();
        }else if(time >= 5000 && time < 10000){
            segments = SegmentsEnum.TIMES_5000.getText();
        }else if(time >= 10000 && time < 20000){
            segments = SegmentsEnum.TIMES_10000.getText();
        }else if(time >= 20000 && time < 60000){
            segments = SegmentsEnum.TIMES_20000.getText();
        }else if(time >= 60000){
            segments = SegmentsEnum.TIMES_60000.getText();
        }
        return segments;
    }
}
