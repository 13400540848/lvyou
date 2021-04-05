package org.ume.school.modules.model.enums;

/*
 * 状态（已开奖1、待开奖0）
 */
public enum PlayResultStatus {
	WAIT("待开奖", 0),  
	FINISHED("已开奖", 1);
    
    private String text;
    private int value;
    
    // 构造方法
    private PlayResultStatus(String text, int value) {
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
