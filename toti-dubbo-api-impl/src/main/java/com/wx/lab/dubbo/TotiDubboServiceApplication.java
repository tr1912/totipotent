package com.wx.lab.dubbo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wangxiao@ybm100.com
 * @date 2020-11-20 上午10:34
 * @projectname totipotent
 */
@SpringBootApplication(scanBasePackages = {"com.wx.lab.dubbo"})
public class TotiDubboServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TotiDubboServiceApplication.class, args);}
}
