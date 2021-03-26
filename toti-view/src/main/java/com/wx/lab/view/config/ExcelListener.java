package com.wx.lab.view.config;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：Nickels
 * @date ：2020/7/27
 * @desc ：
 */
// 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
public class ExcelListener<T> extends AnalysisEventListener<T> {

    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 1000;
    List<T> list = new ArrayList<T>();
    /**
     * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。这里我们传入自己的function作为参数
     */
    ExcelConsumer<T> consumer;

    public ExcelListener() {
    }

    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     */
    public ExcelListener(ExcelConsumer<T> consumer) {
        this.consumer = consumer;
    }


    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        list.add(t);
        if (list.size() > BATCH_COUNT){
            //超出界限值，保存数据库，避免oom
            //执行函数
            consumer.excute(list);

            // 存储完成清理 list
            list.clear();

        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        consumer.excute(list);
    }

}