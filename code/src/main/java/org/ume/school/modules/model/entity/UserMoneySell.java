package org.ume.school.modules.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.ume.school.modules.model.enums.SellStatus;

/**
 * 我要卖
 * Created by Zz on 2018/9/2.
 */
@Entity
@Table(name = "uc_user_money_sell")
public class UserMoneySell implements Serializable {
    
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
    @Column(name = "type_id", columnDefinition = "varchar(64) NOT NULL COMMENT '币种编号'")
    private String typeId;
    
    /**
     * 卖出总金额
     */
    @Column(name = "money", columnDefinition = "DOUBLE(18,8) DEFAULT 0 COMMENT '卖出总金额'")
    private Double money;
    
    /**
     * 剩下金额
     */
    @Column(name = "leaveMoney", columnDefinition = "DOUBLE(18,8) DEFAULT 0 COMMENT '剩下金额'")
    private Double leaveMoney;
    
    /**
     * 支持币种编号
     */
    @Column(name = "buy_type_id", columnDefinition = "varchar(64) NOT NULL COMMENT '支持币种'")
    private String buyTypeId;
    
    /**
     * 状态
     */
    @Column(name = "status", columnDefinition = " tinyint(4) DEFAULT -1 COMMENT '状态 0：正常；1、已取消'")
    private Integer status = null;
    
    /**
     * 添加时间
     */
    @Column(name = "create_time", columnDefinition = "datetime DEFAULT NULL COMMENT '添加时间'")
    private Date createTime;
    
    @Transient
    private String userName;
    @Transient
    private String realName;
    @Transient
    private String typeName;
    @Transient
    private String buyTypeName;
    @Transient
    private String statusName;
    @Transient
    private String excludeUserId;
    
    public UserMoneySell() {
        
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

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Double getLeaveMoney() {
        return leaveMoney;
    }

    public void setLeaveMoney(Double leaveMoney) {
        this.leaveMoney = leaveMoney;
    }

    public String getBuyTypeId() {
        return buyTypeId;
    }

    public void setBuyTypeId(String buyTypeId) {
        this.buyTypeId = buyTypeId;
    }

    public String getBuyTypeName() {
        return buyTypeName;
    }

    public void setBuyTypeName(String buyTypeName) {
        this.buyTypeName = buyTypeName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getExcludeUserId() {
        return excludeUserId;
    }

    public void setExcludeUserId(String excludeUserId) {
        this.excludeUserId = excludeUserId;
    }

    public String getStatusName() {
        if(this.status==null)
            this.statusName = "";
        else if(this.status.intValue() == SellStatus.NORMAL.getValue())
            this.statusName = SellStatus.NORMAL.getText();
        else if(this.status.intValue() == SellStatus.CANCEL.getValue())
            this.statusName = SellStatus.CANCEL.getText();
        else if(this.status.intValue() == SellStatus.SELL_OVER.getValue())
            this.statusName = SellStatus.SELL_OVER.getText();
        else
            this.statusName = "未知状态";
        return this.statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    
    
}
