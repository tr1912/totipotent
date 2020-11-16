package com.wx.lab.view.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wx.lab.view.po.ParamInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ParamInfoMapper extends BaseMapper<ParamInfo> {
    int insertParam (ParamInfo paramInfo) ;
}
