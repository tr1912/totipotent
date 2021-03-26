package com.wx.lab.view.po;

import java.io.Serializable;
import java.util.Date;

/**
 * (UserOrg)实体类
 *
 * @author makejava
 * @since 2020-12-14 21:16:57
 */
public class UserOrg implements Serializable {
    private static final long serialVersionUID = -60762309394104455L;
    /**
     * id
     */
    private Integer id;
    /**
     * 编码
     */
    private String code;
    /**
     * 名称
     */
    private String name;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 删除标记
     */
    private Integer yn;
    /**
     * 仓域标记
     */
    private String orgCode;

    private String tableName;

    private String baseTableName;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getBaseTableName() {
        return baseTableName;
    }

    public void setBaseTableName(String baseTableName) {
        this.baseTableName = baseTableName;
    }

}
