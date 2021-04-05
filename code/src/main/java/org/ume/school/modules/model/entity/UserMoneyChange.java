package org.ume.school.modules.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 用户资产兑换
 * Created by Zz on 2018/10/15.
 */
@Entity
@Table(name = "uc_user_money_change")
public class UserMoneyChange implements Serializable {
    
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
    @Column(name = "from_type_id", columnDefinition = "varchar(64) NOT NULL COMMENT '币种编号'")
    private String fromTypeId;
    
    /**
     * 兑换金额
     */
    @Column(name = "from_money", columnDefinition = "DOUBLE(18,8) DEFAULT 0 COMMENT '兑换金额'")
    private Double fromMoney;
    
    
    /**
     * 兑换结果币种编号
     */
    @Column(name = "to_type_id", columnDefinition = "varchar(64) NOT NULL COMMENT '兑换结果币种编号'")
    private String toTypeId;
    
    /**
     * 兑换结果金额
     */
    @Column(name = "to_money", columnDefinition = "DOUBLE(18,8) DEFAULT 0 COMMENT '兑换结果金额'")
    private Double toMoney;
    
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
     * 添加时间
     */
    @Column(name = "create_time", columnDefinition = "datetime DEFAULT NULL COMMENT '添加时间'")
    private Date createTime;
    
    @Transient
    private String fromTypeName;
    
    @Transient
    private String toTypeName;
    
    @Transient
    private int changeType;
    
    public UserMoneyChange() {
        
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

    public String getFromTypeId() {
        return fromTypeId;
    }

    public void setFromTypeId(String fromTypeId) {
        this.fromTypeId = fromTypeId;
    }

    public Double getFromMoney() {
        return fromMoney;
    }

    public void setFromMoney(Double fromMoney) {
        this.fromMoney = fromMoney;
    }

    public String getToTypeId() {
        return toTypeId;
    }

    public void setToTypeId(String toTypeId) {
        this.toTypeId = toTypeId;
    }

    public Double getToMoney() {
        return toMoney;
    }

    public void setToMoney(Double toMoney) {
        this.toMoney = toMoney;
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

    public String getFromTypeName() {
        return fromTypeName;
    }

    public void setFromTypeName(String fromTypeName) {
        this.fromTypeName = fromTypeName;
    }

    public String getToTypeName() {
        return toTypeName;
    }

    public void setToTypeName(String toTypeName) {
        this.toTypeName = toTypeName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getChangeType() {
        return changeType;
    }

    public void setChangeType(int changeType) {
        this.changeType = changeType;
    }
    
}
