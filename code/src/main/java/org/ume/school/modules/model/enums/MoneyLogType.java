package org.ume.school.modules.model.enums;

/*
 * 交易类型：10充值  11提现  20交易买入  21交易卖出  30购买项目  31撤销购买项目  40发币  
 */
public enum MoneyLogType {
    RECHARGE("代币充值", 10),   
    CASH("代币提现", 11),
    BUY("交易买入", 20),
    SELL("交易卖出", 21),
    SELL_CANCEL("交易卖出撤销", 22),
    BUY_PROJECT("购买项目", 30),
    BUY_PROJECT_NO("撤销购买项目", 31),
    INVITE_REWARD("邀请奖励", 40),
    SEND_PROJECT("项目发币", 50),
    CHANGE("DG兑换", 60),
    PLAY("娱乐消费投注", 70),
    PLAY_REWARD("娱乐消费中奖", 71);
    
    private String text;
    private int value;
    
    // 构造方法
    private MoneyLogType(String text, int value) {
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
