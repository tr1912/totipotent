package com.wx.lab.view.dto;


import com.wx.lab.view.exception.BusinessExceptionEnum;

import java.io.Serializable;

/**
 *  返回参数
 * @param <T>
 */
public class ResponseInfo<T> implements Serializable {
    private static final long serialVersionUID = 4349949604100685624L;
    private static final int SUCCESS_CODE = 0;
    //返回码
    private Integer retCode;
    //返回信息
    private String retMsg;
    //返回数据
    private T data;

    public ResponseInfo() {}

    public ResponseInfo(Integer retCode, String retMsg) {
        this.retCode = retCode;
        this.retMsg = retMsg;
    }

    public ResponseInfo(Integer retCode, String retMsg, T data) {
        this.retCode =retCode;
        this.retMsg = retMsg;
        this.data = data;
    }

    public static <T> ResponseInfo<T> successResponseInfo() {
        return new ResponseInfo<>(BusinessExceptionEnum.SUCCESS.getCode(), BusinessExceptionEnum.SUCCESS.getDesc());
    }
    public static <T> ResponseInfo<T> successResponseInfo(T t) {
        return new ResponseInfo<>(BusinessExceptionEnum.SUCCESS.getCode(), BusinessExceptionEnum.SUCCESS.getDesc(), t);
    }

    public static <T> ResponseInfo<T> failureResponseInfo() {
        return new ResponseInfo<>(BusinessExceptionEnum.ERROR_INNER.getCode(), BusinessExceptionEnum.ERROR_INNER.getDesc());
    }
    public static <T> ResponseInfo<T> failureResponseInfo(String error){
        return new ResponseInfo<>(BusinessExceptionEnum.ERROR_CODE.getCode(), error);
    }
    public static <T> ResponseInfo<T> failureResponseInfo(BusinessExceptionEnum exceptionEnum) {
        return new ResponseInfo<>(exceptionEnum.getCode(), exceptionEnum.getDesc());
    }

    public static <T> ResponseInfo<T> failureResponseInfo(Integer retCode, String retMsg) {
        return new ResponseInfo<>(retCode, retMsg);
    }

    /**
     * 是否成功
     *
     * @return
     */
    public boolean isSuccess() {
        return retCode == SUCCESS_CODE;
    }
    /**
     * 是否失败
     *
     * @return
     */
    public boolean isFailure() {
        return !isSuccess();
    }
    public Integer getRetCode() {
        return retCode;
    }

    public void setRetCode(Integer retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}