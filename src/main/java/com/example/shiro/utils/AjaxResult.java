package com.example.shiro.utils;

public class AjaxResult {
    private Integer code;
    private String message;
    private Object data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static AjaxResult success(String message,Object data){
        return createMessage(200,message,data);
    }
    public static AjaxResult error(String message){
        return createMessage(500,message,null);
    }

    public static AjaxResult error(Integer code,String message){
        return createMessage(code,message,null);
    }

    public static AjaxResult createMessage(Integer code, String message, Object data){
        AjaxResult result=new AjaxResult();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }
}
