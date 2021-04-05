package org.ume.school.modules.model.enums;

/*
 * 快3开奖结果模式
 */
public enum PlayThreeMode {
    THREE_SAME("三同号", 1),
    THREE_NO_SAME("三不同号", 2),
    THREE_LINK_ALL("三连号", 3),
    TWO_SAME("二同号", 4);
    
    private String text;
    private int value;
    
    // 构造方法
    private PlayThreeMode(String text, int value) {
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
