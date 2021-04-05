package org.ume.school.modules.model.enums;

/*
 * 双色球奖项
 */
public enum PlaySevenRewardType {
	NO("未中奖", 0),  
	ONE("一等奖", 1),   
    TOW("二等奖", 2),
    THREE("三等奖", 3),
    FOUR("四等奖", 4),
    FIVE("五等奖", 5),
    SIX("六等奖", 6);
    
    private String text;
    private int value;
    
    // 构造方法
    private PlaySevenRewardType(String text, int value) {
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
