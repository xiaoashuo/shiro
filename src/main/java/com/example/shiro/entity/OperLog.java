package com.example.shiro.entity;

import javax.persistence.*;
import java.util.Date;


public class OperLog {
    //日志主键
    private Long operId;
    //操作标题
    private String title;
    //操作类型
    private Integer businessType;
    //入参方法
    private String method;
    //操作人身份
    private Integer operatorType;
    //操作人
    private String operName;
    //请求url
    private String operUrl;
    //请求ip
    private String operIp;
    //操作地点
    private String operLocation;
    //操作参数
    private String operParam;
    //操作状态0正常 1异常
    private Integer status;
    //错误消息
    private String errorMsg;
    //操作时间
    private Date operTime;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getOperId() {
        return operId;
    }

    public void setOperId(Long operId) {
        this.operId = operId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Integer getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(Integer operatorType) {
        this.operatorType = operatorType;
    }

    public String getOperName() {
        return operName;
    }

    public void setOperName(String operName) {
        this.operName = operName;
    }

    public String getOperUrl() {
        return operUrl;
    }

    public void setOperUrl(String operUrl) {
        this.operUrl = operUrl;
    }

    public String getOperIp() {
        return operIp;
    }

    public void setOperIp(String operIp) {
        this.operIp = operIp;
    }

    public String getOperLocation() {
        return operLocation;
    }

    public void setOperLocation(String operLocation) {
        this.operLocation = operLocation;
    }

    public String getOperParam() {
        return operParam;
    }

    public void setOperParam(String operParam) {
        this.operParam = operParam;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Date getOperTime() {
        return operTime;
    }

    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }

    @Override
    public String toString() {
        return "OperLog{" +
                "operId=" + operId +
                ", title='" + title + '\'' +
                ", businessType=" + businessType +
                ", method='" + method + '\'' +
                ", operatorType=" + operatorType +
                ", operName='" + operName + '\'' +
                ", operUrl='" + operUrl + '\'' +
                ", operIp='" + operIp + '\'' +
                ", operLocation='" + operLocation + '\'' +
                ", operParam='" + operParam + '\'' +
                ", status=" + status +
                ", errorMsg='" + errorMsg + '\'' +
                ", operTime=" + operTime +
                '}';
    }
}
