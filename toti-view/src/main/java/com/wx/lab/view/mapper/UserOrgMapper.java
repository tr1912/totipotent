package com.wx.lab.view.mapper;

import com.wx.lab.view.annotation.ReCreateTable;
import com.wx.lab.view.po.UserOrg;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * (UserOrg)表数据库访问层
 *
 * @author makejava
 * @since 2020-12-14 21:17:07
 */
public interface UserOrgMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    UserOrg queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<UserOrg> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param userOrg 实例对象
     * @return 对象列表
     */
    List<UserOrg> queryAll(UserOrg userOrg);

    /**
     * 新增数据
     *
     * @param userOrg 实例对象
     * @return 影响行数
     */
    int insert(UserOrg userOrg);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<UserOrg> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<UserOrg> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<UserOrg> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<UserOrg> entities);

    /**
     * 修改数据
     *
     * @param userOrg 实例对象
     * @return 影响行数
     */
    int update(UserOrg userOrg);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    /**
     * 是否存在此表
     *
     * @param tableName
     * @return
     */
    String showIsExsit(String tableName);

    List<String> showTables();

    /**
     * 展示表结构
     *
     * @param tableName
     * @return
     */
    Map<String, String> showTableStruct(@Param("tableName") String tableName);

    /**
     * 执行任意sql
     *
     * @param sql
     * @return
     */
    int excute(@Param("sql") String sql);

    /**
     * 按照表插入
     *
     * @param userOrg
     * @return
     */
    @ReCreateTable(baseTable = "user_org")
    int insertByTable(UserOrg userOrg);
}
