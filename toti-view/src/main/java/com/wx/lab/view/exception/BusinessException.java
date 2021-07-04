package com.wx.lab.view.exception;

/**
 * @Description 系统全局异常
 */
public class BusinessException extends Exception {
    private static final long serialVersionUID = 5540719809564720096L;

    private Integer errorCode;
    private String errorMessage;

    public BusinessException(Integer errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
    /**
     * @desc: 通过枚举值构造业务异常
     * @param businessExceptionEnum 业务异常枚举值
     * @return:
     * @author: hbt
     */
    public BusinessException(BusinessExceptionEnum businessExceptionEnum) {
        super(businessExceptionEnum.getDesc());
        this.errorCode = businessExceptionEnum.getCode();
        this.errorMessage = businessExceptionEnum.getDesc();
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}