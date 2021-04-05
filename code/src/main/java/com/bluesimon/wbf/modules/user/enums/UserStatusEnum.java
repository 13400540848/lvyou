package com.bluesimon.wbf.modules.user.enums;

/**
 * 删除标记 0：可用；1、已删除；2、禁用
 * */
public enum UserStatusEnum {
    DELETED(1),
    CLOSED(2),
    NORMAL(0);
    
    private int value;
    
 // 构造方法
    private UserStatusEnum(int value) {
        this.value = value;
    }   
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
