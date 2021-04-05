package com.bluesimon.wbf.modules.menu;

/**
 * 打开方式，0 默认 1 弹出新页面
 * */
public enum OpenModeEnum {    
    DEFAULT("默认", 0),   
    OPEN("弹出新页面", 1);
    
    private String text;
    private int value;
    
    // 构造方法
    private OpenModeEnum(String text, int value) {
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
