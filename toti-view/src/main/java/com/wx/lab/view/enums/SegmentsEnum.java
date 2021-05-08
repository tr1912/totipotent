package com.wx.lab.view.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author duyujie@ybm100.com
 * @Title: SegmentsEnum
 * @Description:
 * @Company: ybm100.com
 * @Created on 2020/5/18
 * @ModifiedBy:
 * @Copyright: Copyright (c) 2019
 */
public enum SegmentsEnum {

    TIMES_0((byte)1, "1"),
    TIMES_100((byte)2,"2"),
    TIMES_1000((byte)3,"3"),
    TIMES_2500((byte)4,"4"),
    TIMES_5000((byte)5,"5"),
    TIMES_10000((byte)6,"6"),
    TIMES_20000((byte)7,"7"),
    TIMES_60000((byte)8,"8");

    /**
     * code
     */
    private byte code;

    /**
     * 名称
     */
    private String text;

    SegmentsEnum(byte code, String text) {
        this.code = code;
        this.text = text;
    }

    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    private static final Map<Byte, SegmentsEnum> map = new HashMap<Byte, SegmentsEnum>();

    static {
        for (SegmentsEnum t : SegmentsEnum.values()) {
            map.put(t.getCode(), t);
        }
    }

    public static SegmentsEnum idOf(String code) {
        return map.get(code);
    }
}
