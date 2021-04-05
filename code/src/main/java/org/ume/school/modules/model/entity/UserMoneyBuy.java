package org.ume.school.modules.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 我要买
 * Created by Zz on 2018/9/2.
 */
@Entity
@Table(name = "uc_user_money_buy")
public class UserMoneyBuy implements Serializable {
    
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
     * 卖单号
     */
    @Column(name = "sell_id", columnDefinition = "varchar(64) NOT NULL COMMENT '卖单号'")
    private String sellId;
    
    /**
     * 用户编号
     */
    @Column(name = "user_id", columnDefinition = "varchar(64) NOT NULL COMMENT '会员id'")
    private String userId;
    
    /**
     * 币种
     */
    @Column(name = "type_id", columnDefinition = "varchar(64) NOT NULL COMMENT '币种编号'")
    private String typeId;
    
    /**
     * 买入金额
     */
    @Column(name = "money", columnDefinition = "DOUBLE(18,8) DEFAULT 0 COMMENT '充提金额'")
    private Double money;
    
    /**
     * 手续费
     */
    @Column(name = "broke_money", columnDefinition = "DOUBLE(18,8) DEFAULT 0 COMMENT '手续费'")
    private Double  brokeMoney;
    
    /**
     * 实际金额
     */
    @Column(name = "real_money", columnDefinition = "DOUBLE(18,8) DEFAULT 0 COMMENT '实际金额'")
    private Double  realMoney;
    
    /**
     * 添加时间
     */
    @Column(name = "create_time", columnDefinition = "datetime DEFAULT NULL  COMMENT '添加时间'")
    private Date createTime;
        
    @Transient
    private String userName;
    @Transient
    private String realName;
    @Transient
    private String typeName;
    
    public UserMoneyBuy() {
        
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSellId() {
        return sellId;
    }

    public void setSellId(String sellId) {
        this.sellId = sellId;
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

}
