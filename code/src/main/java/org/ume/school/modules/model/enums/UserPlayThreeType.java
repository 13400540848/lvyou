package org.ume.school.modules.model.enums;

/*
 * 快3奖项
 */
public enum UserPlayThreeType {
	SUM("和值", 1),
	
	ONE_ALL("一个号通选", 10),
	
	TWO_SAME_ALL("二同号复选", 20),
    TWO_SAME_SINGLE("二同号单选", 21),
    TWO_NO_SAME_ALL("二不同号", 22),
    
    THREE_SAME_ALL("三同号通选", 30),
    THREE_SAME_SINGLE("三同号单选", 31),
    THREE_NO_SAME("三不同号", 32),
    THREE_LINK_ALL("三连号通选", 33),
    
    
    
    NO("未中奖", 0);
    
    private String text;
    private int value;
    
    // 构造方法
    private UserPlayThreeType(String text, int value) {
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
