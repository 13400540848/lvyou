package org.ume.school.modules.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 用户投注快3（详情）
 * Created by Zz on 2018/11/1.
 */
@Entity
@Table(name = "uc_user_play_three_info")
public class UserPlayThreeInfo implements Serializable {
    
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
     * 期数 20181031073
     */
    @Column(name = "play_time", columnDefinition = "DOUBLE(16,2) DEFAULT 0 COMMENT '期数 20181031073'")
    private Double playTime;
    
    /**
     * 用户编号
     */
    @Column(name = "user_id", columnDefinition = "varchar(64) NOT NULL COMMENT '会员id'")
    private String userId;
    
    /**
     * 投注类型（UserPlayThreeType）
     */
    @Column(name = "mode", columnDefinition = "int(1) DEFAULT 0 COMMENT '投注类型'")
    private Integer mode;
    
    /**
     * 选号
     */
    @Column(name = "number", columnDefinition = "varchar(128) NOT NULL COMMENT '选号'")
    private String number;
    
    /**
     * 金额
     */
    @Column(name = "money", columnDefinition = "DOUBLE(10,2) DEFAULT 0 COMMENT '金额'")
    private Double money;
    
    /**
     * 中奖情况（待开奖0 未中奖 -1 中奖1）
     */
    @Column(name = "status", columnDefinition = "int(2) DEFAULT 0 COMMENT '中奖情况（待开奖0 未中奖 -1 中奖1）'")
    private Integer status = 0 ;
    
    /**
     * 中奖金额
     */
    @Column(name = "reward_money", columnDefinition = "DOUBLE(10,2) DEFAULT 0 COMMENT '中奖金额'")
    private Double rewardMoney;
    
    /**
     * 中奖奖项
     */
    @Column(name = "reward_code", columnDefinition = "int(4) DEFAULT 0 COMMENT '中奖奖项'")
    private Integer rewardCode;
    
    /**
     * 投注时间
     */
    @Column(name = "create_time", columnDefinition = "datetime DEFAULT NULL COMMENT '投注时间'")
    private Date createTime;
    
    /**
     * 开奖时间
     */
    @Column(name = "publish_time", columnDefinition = "datetime DEFAULT NULL COMMENT '开奖时间'")
    private Date publishTime;
        
    @Transient
    private String userName;
    
    public UserPlayThreeInfo() {
        
    }
    

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}	

	public Integer getMode() {
        return mode;
    }


    public void setMode(Integer mode) {
        this.mode = mode;
    }


    public String getNumber() {
        return number;
    }


    public void setNumber(String number) {
        this.number = number;
    }


    public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getRewardMoney() {
        return rewardMoney;
    }

    public void setRewardMoney(Double rewardMoney) {
        this.rewardMoney = rewardMoney;
    }

    public Integer getRewardCode() {
        return rewardCode;
    }

    public void setRewardCode(Integer rewardCode) {
        this.rewardCode = rewardCode;
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public Double getMoney() {
        return money;
    }


    public void setMoney(Double money) {
        this.money = money;
    }


    public Double getPlayTime() {
        return playTime;
    }


    public void setPlayTime(Double playTime) {
        this.playTime = playTime;
    }


    public String getUserId() {
        return userId;
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }
    
}
