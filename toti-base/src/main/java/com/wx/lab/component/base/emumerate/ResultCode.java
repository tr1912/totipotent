//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wx.lab.component.base.emumerate;

public enum ResultCode {
    SUCCESS(0, "成功"),
    PARAM_ILLEGAL(-16, "请求参数非法"),
    PARAM_MISSING(-32, "服务参数缺失"),
    SERVICE_FAILURE(-64, "服务执行异常"),
    NOT_FOUND(-128, "抱歉,请求出错了!"),
    UNKNOWN_ERROR(-256, "网络繁忙,请稍后再试!");

    private Integer code;
    private String msg;

    private ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    @Override
    public String toString() {
        return "ResultCode{code=" + this.code + ", msg='" + this.msg + '\'' + '}';
    }
}
