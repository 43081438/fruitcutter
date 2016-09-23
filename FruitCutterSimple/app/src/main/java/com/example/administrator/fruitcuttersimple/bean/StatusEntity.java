package com.example.administrator.fruitcuttersimple.bean;

/**
 * 类名称：
 * 类描述：
 * 创建人：quzongyang
 * 创建时间：2016/9/23. 11:22
 * 版本：
 */
public class StatusEntity extends BaseEntity  {
    private int code;
    private String errmsg;
    private String message;
    private int requestType;


    public StatusEntity() {
        super();
    }
    public StatusEntity(int code, String errmsg, String message) {
        super();
        this.code = code;
        this.errmsg = errmsg;
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getErrmsg() {
        return errmsg;
    }
    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public int getRequestType() {
        return requestType;
    }
    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }


}
