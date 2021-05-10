package com.wx.lab.view.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.Future;

/**
 * @author wangxiao@ybm100.com
 * @date 2021-05-08 18:36
 * @projectname totipotent
 */
public interface AsyncService {

    Integer execute01Async();

    Integer execute02Async();

    Future<Integer> execute01AsyncWithFuture();

    Future<Integer> execute02AsyncWithFuture();

    ListenableFuture<Integer> execute01AsyncWithListenableFuture();

    Integer execute01();

    Integer execute02();

    Integer zhaoDaoNvPengYou(Integer a, Integer b);
}
