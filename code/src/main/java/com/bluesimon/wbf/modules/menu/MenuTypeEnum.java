package com.bluesimon.wbf.modules.menu;

/**
 * 类型，0 门户 1菜单
 * */
public enum MenuTypeEnum {    
    NAV("门户", 0),   
    MENU("菜单", 1);
    
    private String text;
    private int value;
    
    // 构造方法
    private MenuTypeEnum(String text, int value) {
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
