package org.ume.school.modules.model.enums;

/*
 * 审核标记 0：待审核；1、通过；2、不通过
 */
public enum CheckStatus {
    UN_CHECK("待审核", 0),   
    AGREE("提现成功", 1),
    NOT_AGREE("提现失败", 2);
    
    private String text;
    private int value;
    
    // 构造方法
    private CheckStatus(String text, int value) {
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
