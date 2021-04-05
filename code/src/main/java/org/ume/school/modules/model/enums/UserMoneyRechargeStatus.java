package org.ume.school.modules.model.enums;

/*
 * 审核标记 -2：已取消 -1：待支付 0：待审核；1：成功；2：失败
 */
public enum UserMoneyRechargeStatus {
    CANCLE("已取消", -2),
    PAY_WAIT("待支付", -1),
    SEND_WAIT("待发币", 0), 
    SUCCESS("充值成功", 1),
    FAILTRUE("充值失败", 2);
    
    private String text;
    private int value;
    
    // 构造方法
    private UserMoneyRechargeStatus(String text, int value) {
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
