//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wx.lab.component.base.common;

import java.io.Serializable;

public class Result<T> implements Serializable {
    private static final long serialVersionUID = 7796751570714827293L;

    /**
     * 状态码
     */
    private int code;
    /**
     * 描述
     */
    private String msg;
    /**
     * 结果集
     */
    private T result;

    public Result() {
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(int code, T result) {
        this.code = code;
        this.result = result;
    }

    public Result(int code, String msg, T result) {
        this.code = code;
        this.msg = msg;
        this.result = result;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return this.result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
