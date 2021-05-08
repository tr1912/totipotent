package com.wx.lab.view.context;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;

/**
 * @author duyujie@ybm100.com
 * @Title: BaseModel
 * @Description:
 * @Company: ybm100.com
 * @Created on 2019/9/25 16:52
 * @ModifiedBy:
 * @Copyright: Copyright (c) 2019
 */
public class BaseModel implements Serializable {
    private static final long serialVersionUID = -966372351458114170L;
    /**
     * @return
     *
     * @see Object#toString()
     */
    @Override
    public String toString() {
        try {
            return ToStringBuilder.reflectionToString(this,
                    ToStringStyle.SHORT_PREFIX_STYLE);
        } catch (Exception e) {
            // NOTICE: 这样做的目的是避免由于toString()的异常导致系统异常终止
            // 大部分情况下，toString()用在日志输出等调试场景
            return super.toString();
        }
    }
}
