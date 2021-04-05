package com.bluesimon.wbf;

/**
 * Created by Django on 2017/7/16.
 */
public class Response<T> {

    public static final String OK = "0";
    public static final String NORMAL = "-100";

    private String resultCode = OK;

    private String resultMsg;

    private long total;
    
    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    private T rows;

    public Response() {
        
    }
    public Response(ResponseErrorEnum errorEnum) {
        this.resultCode = errorEnum.getCode();
        this.resultMsg = errorEnum.getMessage();
    }
    public Response(String resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }
    public Response(String resultMsg) {
        this.resultCode = NORMAL;
        this.resultMsg = resultMsg;
    }

    public Response(T rows) {
        this.rows = rows;
    }

    public T getRows() {
        return rows;
    }

    public void setRows(T rows) {
        this.rows = rows;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
    
    public void setResponse(String resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }
    
    public void setByErrorEnum(ResponseErrorEnum errorEnum){
        this.resultCode = errorEnum.getCode();
        this.resultMsg = errorEnum.getMessage();
    }
}
