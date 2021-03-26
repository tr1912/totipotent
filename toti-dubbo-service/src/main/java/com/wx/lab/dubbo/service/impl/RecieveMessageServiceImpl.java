package com.wx.lab.dubbo.service.impl;


import com.wx.lab.dubbo.service.RecieveMessageService;
import org.springframework.stereotype.Service;

/**
 * @author wangxiao@ybm100.com
 * @date 2020-11-20 下午4:25
 * @projectname totipotent
 */
@Service
public class RecieveMessageServiceImpl implements RecieveMessageService {

    @Override
    public int pullMqMessage() {
        return 0;
    }
}
