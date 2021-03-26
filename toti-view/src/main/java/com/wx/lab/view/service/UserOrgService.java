package com.wx.lab.view.service;

/**
 * @author wangxiao@ybm100.com
 * @date 2020-12-14 下午9:22
 * @projectname totipotent
 */
public interface UserOrgService {

    /**
     * 谨慎插入
     *
     * @param code
     * @return
     */
    boolean carefulCreate(String code);
}
