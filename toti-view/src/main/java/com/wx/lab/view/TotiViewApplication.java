package com.wx.lab.view;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wangxiao@ybm100.com
 * @date 2020-11-03 10:03
 * @projectname totipotent
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.wx.lab.view.mapper"})
public class TotiViewApplication {

    public static void main(String[] args) {
        SpringApplication.run(TotiViewApplication.class, args);
    }
}
