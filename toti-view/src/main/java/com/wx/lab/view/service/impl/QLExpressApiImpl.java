package com.wx.lab.view.service.impl;

import com.alibaba.fastjson.JSON;
import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import com.wx.lab.view.service.QLExpressApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wx
 * @version 1.0.0
 * @Description
 * @createTime 2021-04-12 19:28:28
 */
@Slf4j
@Service
public class QLExpressApiImpl implements QLExpressApi {

    @Value("${qlexpress.import.Class}")
    String importClass;

    /**
     * 执行规则
     *
     * @param param         参数
     * @param expressString 脚本字符串
     * @return
     */
    @Override
    public Object startExecute(Object param, String expressString) {
        try {
            if (!StringUtils.isEmpty(expressString)) {
                ExpressRunner runner = new ExpressRunner();
                DefaultContext<String, Object> context = new DefaultContext<>();
                context.put("param", param);
                // isCache 是否输出详细的执行指令信息
                // isTrace 是否输出详细的执行指令信息
                // errorList 输出的错误信息List
                List<String> errorList = new ArrayList<>();
                expressString = importClass + expressString;
                Object result = runner.execute(expressString, context, errorList, true, false);
                if (!CollectionUtils.isEmpty(errorList)) {
                    log.info("ql解析异常信息：{}", JSON.toJSONString(errorList));
                }
                return result;
            }
        } catch (Exception e) {
            log.error("解析规则出现异常:", e);
            return null;
        }
        return null;
    }

    /**
     * 获得String类型的ql结果
     *
     * @param param         参数
     * @param expressString ql语句
     * @return
     */
    @Override
    public String getQlString(Object param, String expressString) {
        if (StringUtils.isEmpty(expressString)) {
            return "";
        }
        Object res = startExecute(param, expressString);
        if (res == null) {
            return "";
        }
        if (res instanceof String) {
            return (String) res;
        } else {
            log.info("ql结果不是String类型！");
            return "";
        }
    }
}
