package com.wx.lab.view.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * AsyncUncaughtExceptionHandler 只能拦截返回类型非 Future 的异步调用方法
 */
@Slf4j
@Component
public class GlobalAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
//        log.error("[handleUncaughtException][method({}) params({}) 发生异常]",
//                method, params, ex);
    }

}
