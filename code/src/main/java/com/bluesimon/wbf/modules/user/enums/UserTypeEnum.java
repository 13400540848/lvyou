package com.bluesimon.wbf.modules.user.enums;

/**
 * 账号类型  1、超级管理员（所有权限）；2、后台用户 ；  0: 外部注册用户 ; 
 * */
public enum UserTypeEnum {
    ADMIN(1),
    SYSTEM(2),
    USER(0);
    
    private int value;
    
 // 构造方法
    private UserTypeEnum(int value) {
        this.value = value;
    }   
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
