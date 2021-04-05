package org.ume.school.modules.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.ume.school.modules.model.enums.PlayThreeRewardType;
import org.ume.school.modules.model.enums.UserPlayStatus;
import org.ume.school.modules.model.enums.UserPlayThreeType;

/**
 * 用户投注快3
 * Created by Zz on 2018/11/1.
 */
@Entity
@Table(name = "uc_user_play_three")
public class UserPlayThree implements Serializable {
    
    /**	
     * Member Description
     */
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 订单号
     */
    @Column(name = "order_id", columnDefinition = "varchar(64) NOT NULL COMMENT '订单号'")
    @Id
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
     * 投注数量
     */
    @Column(name = "count_number", columnDefinition = "int(11) DEFAULT 0 COMMENT '投注数量'")
    private Integer countNumber;
    
    /**
     * 单注金额
     */
    @Column(name = "per_money", columnDefinition = "DOUBLE(10,2) DEFAULT 0 COMMENT '单注金额'")
    private Double perMoney;
    
    /**
     * 总金额
     */
    @Column(name = "sum_money", columnDefinition = "DOUBLE(10,2) DEFAULT 0 COMMENT '总金额'")
    private Double sumMoney;
    
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
     * 中奖数量
     */
    @Column(name = "count_reward", columnDefinition = "int(11) DEFAULT 0 COMMENT '中奖数量'")
    private Double countReward;
    
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
    @Transient
    private String modeName;
    @Transient
    private String statusName;
    @Transient
    private String rewardCodeName;
    
    
    public UserPlayThree() {
        
    }

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

    public Integer getCountNumber() {
		return countNumber;
	}

	public void setCountNumber(Integer countNumber) {
		this.countNumber = countNumber;
	}

	public Double getPerMoney() {
		return perMoney;
	}

	public void setPerMoney(Double perMoney) {
		this.perMoney = perMoney;
	}

	public Double getSumMoney() {
		return sumMoney;
	}

	public void setSumMoney(Double sumMoney) {
		this.sumMoney = sumMoney;
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

	public String getModeName() {
	    if(this.mode == null)
	        this.modeName = "";
	    else{
    	    for(UserPlayThreeType p : UserPlayThreeType.values()){
    	        if(p.getValue() == this.mode.intValue()){
    	            this.modeName = p.getText();
    	            break;
    	        }
    	    }
	    }
        return this.modeName;
	}

	public void setModeName(String modeName) {
		this.modeName = modeName;
	}	

    public String getStatusName() {
        if(this.status == null)
            this.statusName = "";
        else{
            for(UserPlayStatus p : UserPlayStatus.values()){
                if(p.getValue() == this.status.intValue()){
                    this.statusName = p.getText();
                    break;
                }
            }
        }
        return this.statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }    

    public String getRewardCodeName() {
        if(this.rewardCode == null)
            this.rewardCodeName = "";
        else{
            for(PlayThreeRewardType p : PlayThreeRewardType.values()){
                if(p.getValue() == this.rewardCode.intValue()){
                    this.rewardCodeName = p.getText();
                    break;
                }
            }
        }
        return this.rewardCodeName;
	}

	public void setRewardCodeName(String rewardCodeName) {
		this.rewardCodeName = rewardCodeName;
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

    public Double getCountReward() {
        return countReward;
    }

    public void setCountReward(Double countReward) {
        this.countReward = countReward;
    }

    
}
