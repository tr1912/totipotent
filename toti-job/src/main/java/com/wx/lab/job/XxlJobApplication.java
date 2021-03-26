package com.wx.lab.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wangxiao@ybm100.com
 * @date 2020-11-17 上午12:07
 * @projectname totipotent
 */
@SpringBootApplication(scanBasePackages = {"com.wx.lab.job"})
public class XxlJobApplication {
    public static void main(String[] args) {
        SpringApplication.run(XxlJobApplication.class, args);
    }
}
