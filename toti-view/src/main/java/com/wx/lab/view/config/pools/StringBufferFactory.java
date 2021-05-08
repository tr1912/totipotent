package com.wx.lab.view.config.pools;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;

public class StringBufferFactory
    extends BasePooledObjectFactory<StringBuffer> {

//    @Override
//    public StringBuffer create() {
//        return new StringBuffer();
//    }
//
//    /**
//     * Use the default PooledObject implementation.
//     */
//    @Override
//    public PooledObject<StringBuffer> wrap(StringBuffer buffer) {
//        return new DefaultPooledObject<StringBuffer>(buffer);
//    }
//
//    /**
//     * When an object is returned to the pool, clear the buffer.
//     */
//    @Override
//    public void passivateObject(PooledObject<StringBuffer> pooledObject) {
//        pooledObject.getObject().setLength(0);
//    }
//
//    // for all other methods, the no-op implementation
//    // in BasePooledObjectFactory will suffice


    @Override
    public StringBuffer create() throws Exception {
        return new StringBuffer();
    }
    // 直接使用DefaultPooledObject包装真正对象StringBuffer，提供池化对象
    @Override
    public PooledObject<StringBuffer> wrap(StringBuffer obj) {
        return new DefaultPooledObject<>(obj);
    }
    @Override
    public void passivateObject(PooledObject<StringBuffer> p) throws Exception {
        p.getObject().setLength(0);
    }

    public static void main(String[] args) throws Exception {
        // 直接使用 apache common pool2提供的对象池，将创建池化对象细节委托给自定义的工厂即可。
        GenericObjectPool<StringBuffer> pool = new GenericObjectPool<>(new StringBufferFactory());
        // 从对象池借真正对象
        StringBuffer stringBuffer = pool.borrowObject();
        // do something with string buffer.
        // 归还
        pool.returnObject(stringBuffer);
    }

}