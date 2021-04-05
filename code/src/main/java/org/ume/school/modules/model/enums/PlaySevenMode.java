package org.ume.school.modules.model.enums;

/*
 * 双色球投注模式
 */
public enum PlaySevenMode {
	SINGLE("单式", 0),  
	MULTIPLE("复式", 1);
    
    private String text;
    private int value;
    
    // 构造方法
    private PlaySevenMode(String text, int value) {
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
