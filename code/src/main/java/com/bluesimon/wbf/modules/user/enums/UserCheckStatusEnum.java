package com.bluesimon.wbf.modules.user.enums;

/**
 * 认证标记 -1：待提交认证交 0：待审核；1、通过；2、不通过'
 * */
public enum UserCheckStatusEnum {    
    UN_SUBMIT("待提交认证", -1),   
    UN_CHECK("待审核", 0),
    NORMAL("认证通过", 1),
    UN_AREAA("认证不通过", 2);
    
    private String text;
    private int value;
    
    // 构造方法
    private UserCheckStatusEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }   
    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
}
