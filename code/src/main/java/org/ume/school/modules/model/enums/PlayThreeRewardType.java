package org.ume.school.modules.model.enums;

/*
 * 快3奖项
 */
public enum PlayThreeRewardType {
    SUM_3("和值3", 3),   
	SUM_4("和值4", 4),   
	SUM_5("和值5", 5),  
	SUM_6("和值6", 6),   
	SUM_7("和值7", 7),  
	SUM_8("和值8", 8),   
	SUM_9("和值9", 9),  
	SUM_10("和值10", 10),   
	SUM_11("和值11", 11),  
	SUM_12("和值12", 12),   
	SUM_13("和值13", 13),  
	SUM_14("和值14", 14),   
	SUM_15("和值15", 15),  
	SUM_16("和值16", 16),   
	SUM_17("和值17", 17),
	SUM_18("和值18", 18),
	
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
    private PlayThreeRewardType(String text, int value) {
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
