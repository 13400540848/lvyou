package org.ume.school.modules.model.enums;

/*
 * 币种类型：0代币 1投资币种 2系统
 */
public enum MoneyTypeMode {
    MONEY("代币", 0),   
    INVEST("投资币种", 1),
    SYSTEM("系统", 2);
    
    private String text;
    private int value;
    
    // 构造方法
    private MoneyTypeMode(String text, int value) {
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
