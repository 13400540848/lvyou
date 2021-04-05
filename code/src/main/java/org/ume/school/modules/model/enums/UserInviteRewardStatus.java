package org.ume.school.modules.model.enums;

/*
 * 状态 0：待实名认证；1：已奖励
 */
public enum UserInviteRewardStatus {
    WAIT_CHECK("待邀请用户实名认证后奖励", 0), 
    SUCCESS("已奖励", 1);
    
    private String text;
    private int value;
    
    // 构造方法
    private UserInviteRewardStatus(String text, int value) {
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
