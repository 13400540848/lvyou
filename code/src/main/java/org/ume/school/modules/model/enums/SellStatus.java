package org.ume.school.modules.model.enums;

/*
 * 状态 0：正常；1、已取消
 */
public enum SellStatus {
    NORMAL("正常", 0),   
    CANCEL("已撤销", 1),
    SELL_OVER("已卖完", 2);
    
    private String text;
    private int value;
    
    // 构造方法
    private SellStatus(String text, int value) {
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
