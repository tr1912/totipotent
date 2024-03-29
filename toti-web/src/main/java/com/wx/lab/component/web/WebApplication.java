package com.wx.lab.component.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author wangxiao@ybm100.com
 * @date 2020-11-03 10:03
 * @projectname totipotent
 */
@SpringBootApplication
//@MapperScan(basePackages = {"com.wx.lab.mapper"})
@ComponentScan(basePackages = {"com.wx.lab.component"})
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
