package com.bluesimon.wbf.enums;

/**
 * 是否： 1是 0否
 * */
public enum YesNoEnum {    
    YES("是", 1),   
    NO("否", 0);
    
    private String text;
    private int value;
    
    // 构造方法
    private YesNoEnum(String text, int value) {
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
