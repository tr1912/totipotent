package com.wx.lab.view.service.impl;

import com.wx.lab.view.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.Future;

@Slf4j
@Service
public class AsyncServiceImpl implements AsyncService {

    @Override
    @Async
    public Integer execute01Async() {
        return this.execute01();
    }

    @Override
    @Async
    public Integer execute02Async() {
        return this.execute02();
    }

    @Override
    @Async
    public Future<Integer> execute01AsyncWithFuture() {
        return AsyncResult.forValue(this.execute01());
    }

    @Override
    @Async
    public Future<Integer> execute02AsyncWithFuture() {
        return AsyncResult.forValue(this.execute02());
    }

    @Override
    @Async
    public ListenableFuture<Integer> execute01AsyncWithListenableFuture() {
        try {
            return AsyncResult.forValue(this.execute02());
        } catch (Throwable ex) {
            return AsyncResult.forExecutionException(ex);
        }
    }

    @Override
    public Integer execute01() {
        log.info("[execute01]");
        sleep(10);
        return 1;
    }

    @Override
    public Integer execute02() {
        log.info("[execute02]");
        sleep(5);
        return 2;
    }

    private static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Async
    public Integer zhaoDaoNvPengYou(Integer a, Integer b) {
        throw new RuntimeException("程序员不需要女朋友");
    }

}
