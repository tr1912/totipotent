package com.wx.lab.design.patterns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author wangxiao
 * @email wangxiao@ybm100.com
 * @date 2021-07-01 0001 9:42
 * Project totipotent
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DesignPatternsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DesignPatternsApplication.class, args);
    }
}
