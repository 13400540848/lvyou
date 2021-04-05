package org.ume.school.modules.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.ume.school.modules.model.enums.CheckStatus;

/**
 * 提现
 * Created by Zz on 2018/9/2.
 */
@Entity
@Table(name = "uc_user_money_cash")
public class UserMoneyCash implements Serializable {
    
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
     * 订单号
     */
    @Column(name = "order_id", columnDefinition = "varchar(64) NOT NULL COMMENT '订单号'")
    private String orderId;

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
     * 金额
     */
    @Column(name = "money", columnDefinition = "DOUBLE(18,8) DEFAULT 0 COMMENT '金额'")
    private Double money;
    
    /**
     * 手续费
     */
    @Column(name = "broke_money", columnDefinition = "DOUBLE(18,8) DEFAULT 0 COMMENT '手续费'")
    private Double  brokeMoney;
    
    /**
     * 实际到账
     */
    @Column(name = "real_money", columnDefinition = "DOUBLE(18,8) DEFAULT 0 COMMENT '实际到账金额'")
    private Double  realMoney;
    
    /**
     * 接币地址
     */
    @Column(name = "money_address", columnDefinition = "varchar(128) NOT NULL COMMENT '接币地址'")
    private String moneyAddress;

    /**
     * 添加时间
     */
    @Column(name = "create_time", columnDefinition = "datetime DEFAULT NULL  COMMENT '添加时间'")
    private Date createTime;
    
    /**
     * 审核标记
     */
    @Column(name = "check_status", columnDefinition = " tinyint(4) DEFAULT -1 COMMENT '审核标记 0：待审核；1、通过；2、不通过'")
    private Integer checkStatus = 0;
    
    /**
     * 审核意见
     */
    @Column(name = "check_reason", columnDefinition = " varchar(512) DEFAULT NULL COMMENT '审核意见'")
    private String checkReason;
    
    /**
     * 审核时间
     */
    @Column(name = "check_time", columnDefinition = "datetime DEFAULT NULL  COMMENT '审核时间'")
    private Date checkTime;
    
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
    
    @Transient
    private String typeName;
    @Transient
    private String userName;
    @Transient
    private String realName;
    @Transient
    private String checkStatusName;
    @Transient
    private String dealPassword;
    @Transient
    private String smsCode;    
    
    public UserMoneyCash() {
        
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Double getBrokeMoney() {
        return brokeMoney;
    }

    public void setBrokeMoney(Double brokeMoney) {
        this.brokeMoney = brokeMoney;
    }
    
    public Double getRealMoney() {
        return realMoney;
    }

    public void setRealMoney(Double realMoney) {
        this.realMoney = realMoney;
    }

    public String getMoneyAddress() {
        return moneyAddress;
    }

    public void setMoneyAddress(String moneyAddress) {
        this.moneyAddress = moneyAddress;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getCheckReason() {
        return checkReason;
    }

    public void setCheckReason(String checkReason) {
        this.checkReason = checkReason;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
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

    public String getCheckStatusName() {
        if(this.checkStatus==null)
            this.checkStatusName = "";
        else if(this.checkStatus.intValue() == CheckStatus.AGREE.getValue())
            this.checkStatusName = CheckStatus.AGREE.getText();
        else if(this.checkStatus.intValue() == CheckStatus.NOT_AGREE.getValue())
            this.checkStatusName = CheckStatus.NOT_AGREE.getText();
        else if(this.checkStatus.intValue() == CheckStatus.UN_CHECK.getValue())
            this.checkStatusName = CheckStatus.UN_CHECK.getText();
        else
            this.checkStatusName = "未知状态";
        return this.checkStatusName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getDealPassword() {
        return dealPassword;
    }

    public void setDealPassword(String dealPassword) {
        this.dealPassword = dealPassword;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }
}
