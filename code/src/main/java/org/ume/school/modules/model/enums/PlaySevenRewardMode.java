package org.ume.school.modules.model.enums;

/*
 * 奖励类型（0固定金额、1浮动金额）
 */
public enum PlaySevenRewardMode {
	STATIC("固定金额", 0),  
	DIFFERENCE("浮动金额", 1);
    
    private String text;
    private int value;
    
    // 构造方法
    private PlaySevenRewardMode(String text, int value) {
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
