package com.wx.lab.view.service.impl;

import com.wx.lab.view.mapper.UserOrgMapper;
import com.wx.lab.view.po.UserOrg;
import com.wx.lab.view.service.UserOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author wangxiao@ybm100.com
 * @date 2020-12-14 下午9:23
 * @projectname totipotent
 */
@Service
public class UserOrgServiceImpl implements UserOrgService {

    @Autowired
    private UserOrgMapper userOrgMapper;

    private static final String BASE_TABLE = "user_org";

    /**
     * 谨慎插入
     *
     * @param code
     * @return
     */
    @Override
    public boolean carefulCreate(String code) {
        String tableName = "user_org_002";

        String isExsit = userOrgMapper.showIsExsit(tableName + "%");
        if (StringUtils.isEmpty(isExsit)) {
            // 表不存在的情况
            Map<String, String> sqlMap = userOrgMapper.showTableStruct(BASE_TABLE);
            if (!StringUtils.isEmpty(sqlMap.get("Create Table"))) {
                String sql = sqlMap.get("Create Table");
                // 创建表
                sql = sql.replace("CREATE TABLE `" + BASE_TABLE + "`", "CREATE TABLE `" + tableName + "`");
                userOrgMapper.excute(sql);
            }
        }

        UserOrg userOrg = new UserOrg();
        userOrg.setCode(code == null ? "" : code);
        userOrg.setCreateTime(new Date());
        userOrg.setOrgCode("003");
        userOrg.setYn(1);
        userOrg.setName("wx");
        userOrg.setTableName(tableName);

        int i = userOrgMapper.insertByTable(userOrg);
        return i > 0;
    }
}
