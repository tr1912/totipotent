package com.wx.lab.view;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author wangxiao@ybm100.com
 * @date 2020-11-03 10:03
 * @projectname totipotent
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.wx.lab"})
public class TotiViewApplication {

    public static void main(String[] args) {
        SpringApplication.run(TotiViewApplication.class, args);
    }
}
