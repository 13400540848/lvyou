package org.ume.school.modules.model.enums;

/*
 * 状态 0：正常；1、已发币；2：部分发币
 */
public enum UserMoneyProjectStatus {
    NORMAL("进行中", 0),   
    SEND_FINISHED("已发币", 1),
    SEND_PART("部分发币", 2);
    
    private String text;
    private int value;
    
    // 构造方法
    private UserMoneyProjectStatus(String text, int value) {
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
