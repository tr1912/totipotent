package com.wx.lab.view.controller;

import com.wx.lab.view.entity.QueryParam;
import com.wx.lab.view.entity.RuleResult;
import com.wx.lab.view.service.RuleEngineService;
import com.wx.lab.view.utils.KieUtils;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/rule")
public class RuleController {

//    @Resource
//    private KieSession kieSession;
    @Autowired
    private RuleEngineService ruleEngineService ;

    @RequestMapping("/param")
    public void param (){

        KieSession kieSession = KieUtils.getKieSession();

        QueryParam queryParam1 = new QueryParam() ;
        queryParam1.setParamId("4");
        queryParam1.setParamSign("+");
        QueryParam queryParam2 = new QueryParam() ;
        queryParam2.setParamId("9");
        queryParam2.setParamSign("-");


        // 入参
        FactHandle insert2 = kieSession.insert(queryParam1);
        FactHandle insert1 = kieSession.insert(queryParam2);
        FactHandle insert = kieSession.insert(this.ruleEngineService);

        // 返参
        RuleResult resultParam = new RuleResult() ;
        FactHandle insert3 = kieSession.insert(resultParam);
        int i = kieSession.fireAllRules();
        System.out.println(i);
    }
}
