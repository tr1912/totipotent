package com.wx.lab.view.config.pools;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;

/**
 * @author wangxiao
 * @email wangxiao@ybm100.com
 * @date 2021-04-14 19:18
 * @packagename com.wx.lab.view.config.pools
 */
public class BookFactory extends BasePooledObjectFactory<BookObject> {
    @Override
    public BookObject create() throws Exception {
        return new BookObject();
    }
    // 直接使用DefaultPooledObject包装真正对象StringBuffer，提供池化对象
    @Override
    public PooledObject<BookObject> wrap(BookObject obj) {
        return new DefaultPooledObject<>(obj);
    }
    @Override
    public void passivateObject(PooledObject<BookObject> p) throws Exception {
        p.getObject().setBuff("0-init");
    }

    public static void main(String[] args) throws Exception {
        // 直接使用 apache common pool2提供的对象池，将创建池化对象细节委托给自定义的工厂即可。
        GenericObjectPool<BookObject> pool = new GenericObjectPool<>(new BookFactory());
        // 从对象池借真正对象
        BookObject bookObject1 = pool.borrowObject();
        bookObject1.setBookName("1");
        bookObject1.setBuff("1");
        BookObject bookObject2 = pool.borrowObject();
        bookObject2.setBookName("2");
        bookObject2.setBuff("2");
        // do something with string buffer.
        // 归还
        pool.returnObject(bookObject1);
        pool.returnObject(bookObject2);

        System.out.println(bookObject1);
        System.out.println(bookObject2);
    }
}
