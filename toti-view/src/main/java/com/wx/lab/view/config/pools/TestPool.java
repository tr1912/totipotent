package com.wx.lab.view.config.pools;

import org.apache.commons.pool2.impl.GenericObjectPool;

/**
 * @author wangxiao
 * @email wangxiao@ybm100.com
 * @date 2021-04-14 18:15
 * @packagename com.wx.lab.view.config.pools
 */
public class TestPool {

    public static void main(String[] args) {
        ReaderUtil readerUtil = new ReaderUtil(new GenericObjectPool<StringBuffer>(new StringBufferFactory()));
        System.out.println("1");
    }
}
