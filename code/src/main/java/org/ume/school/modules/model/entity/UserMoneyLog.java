package org.ume.school.modules.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.ume.school.modules.model.enums.MoneyLogType;

/**
 * 用户钱包交易记录
 * Created by Zz on 2018/9/2.
 */
@Entity
@Table(name = "uc_user_money_log")
public class UserMoneyLog implements Serializable {
    
    /**	
     * Member Description
     */
    
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @Column(name = "id", columnDefinition = "varchar(64) NOT NULL COMMENT '编号'")
    @Id
    private String id;

    /**
     * 用户编号
     */
    @Column(name = "user_id", columnDefinition = "varchar(64) NOT NULL COMMENT '会员id'")
    private String userId;
    
    /**
     * 币种编号
     */
    @Column(name = "type_id", columnDefinition = "varchar(64) NOT NULL COMMENT '会员id'")
    private String typeId;
    
    /**
     * 交易类型：10充值  11提现  20交易买入  21交易卖出  30购买项目  31撤销购买项目  40发币  
     */
    @Column(name = "log_type", columnDefinition = "int DEFAULT 1 COMMENT '交易类型：10充值  11提现  20交易买入  21交易卖出  30购买项目  31撤销购买项目  40发币'")
    private Integer logType;
    
    /**
     * 交易金额
     */
    @Column(name = "money", columnDefinition = "DOUBLE(18,8) DEFAULT 0 COMMENT '充提金额'")
    private Double money;
    
    /**
     * 交易前金额
     */
    @Column(name = "before_money", columnDefinition = "DOUBLE(18,8) DEFAULT 0 COMMENT '交易前金额'")
    private Double beforeMoney;
    
    /**
     * 交易后金额
     */
    @Column(name = "after_money", columnDefinition = "DOUBLE(18,8) DEFAULT 0 COMMENT '交易后金额'")
    private Double afterMoney;
    
    /**
     * 交易时间
     */
    @Column(name = "create_time", columnDefinition = "datetime DEFAULT NULL  COMMENT '交易时间'")
    private Date createTime;
    
    /**
     * 管理员ID
     */
    @Column(name = "admin_id", columnDefinition = "varchar(255) DEFAULT NULL COMMENT '管理员ID'")
    private String adminId;

    /**
     * 管理员名称
     */
    @Column(name = "admin_name", columnDefinition = "varchar(255) DEFAULT NULL  COMMENT '管理员名称'")
    private String adminName;
    
    private String typeName;
    private String logTypeName;
    
    public UserMoneyLog() {
        
    }

    public String getLogTypeName() {
        if(this.logType==null)
            this.logTypeName = "";
        else if(this.logType.intValue() == MoneyLogType.BUY.getValue())
            this.logTypeName = MoneyLogType.BUY.getText();
        else if(this.logType.intValue() == MoneyLogType.BUY_PROJECT.getValue())
            this.logTypeName = MoneyLogType.BUY_PROJECT.getText();
        else if(this.logType.intValue() == MoneyLogType.BUY_PROJECT_NO.getValue())
            this.logTypeName = MoneyLogType.BUY_PROJECT_NO.getText();
        else if(this.logType.intValue() == MoneyLogType.CASH.getValue())
            this.logTypeName = MoneyLogType.CASH.getText();
        else if(this.logType.intValue() == MoneyLogType.RECHARGE.getValue())
            this.logTypeName = MoneyLogType.RECHARGE.getText();
        else if(this.logType.intValue() == MoneyLogType.SELL.getValue())
            this.logTypeName = MoneyLogType.SELL.getText();
        else if(this.logType.intValue() == MoneyLogType.SELL_CANCEL.getValue())
            this.logTypeName = MoneyLogType.SELL_CANCEL.getText();
        else if(this.logType.intValue() == MoneyLogType.INVITE_REWARD.getValue())
            this.logTypeName = MoneyLogType.INVITE_REWARD.getText();
        else if(this.logType.intValue() == MoneyLogType.SEND_PROJECT.getValue())
            this.logTypeName = MoneyLogType.SEND_PROJECT.getText();
        else if(this.logType.intValue() == MoneyLogType.CHANGE.getValue())
            this.logTypeName = MoneyLogType.CHANGE.getText();
        else if(this.logType.intValue() == MoneyLogType.PLAY.getValue())
            this.logTypeName = MoneyLogType.PLAY.getText();
        else
            this.logTypeName = "未知类型";
        return this.logTypeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public Integer getLogType() {
        return logType;
    }

    public void setLogType(Integer logType) {
        this.logType = logType;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Double getBeforeMoney() {
        return beforeMoney;
    }

    public void setBeforeMoney(Double beforeMoney) {
        this.beforeMoney = beforeMoney;
    }

    public Double getAfterMoney() {
        return afterMoney;
    }

    public void setAfterMoney(Double afterMoney) {
        this.afterMoney = afterMoney;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    
    
}
