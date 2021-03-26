package com.wx.lab.view.controller;

import com.wx.lab.view.service.UserOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangxiao
 */
@RestController
@RequestMapping("/table")
public class TableController {

    @Autowired
    private UserOrgService userOrgService;

    @GetMapping("/insertData")
    public String insertData(String code){
        boolean flag = userOrgService.carefulCreate(code);
        return "是否成功？" + flag;
    }

}
