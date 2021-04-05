package com.bluesimon.wbf.enums;

/**
 * 状态： 0正常 1停用
 * */
public enum NormalStatusEnum {
    ENABLED("正常", 0),
    DISABLE("停用", 1);

    private String text;
    private int value;

    // 构造方法
    private NormalStatusEnum(String text, int value) {
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
