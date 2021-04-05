package org.ume.school.modules.model.enums;

/*
 * 中奖情况（待开奖0 未中奖 -1 中奖1）
 */
public enum UserPlayStatus {
    NO("未中奖", -1),
    WAIT("待开奖", 0), 
    YES("中奖", 1);
    
    private String text;
    private int value;
    
    // 构造方法
    private UserPlayStatus(String text, int value) {
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
