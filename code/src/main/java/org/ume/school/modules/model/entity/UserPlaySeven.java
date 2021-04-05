package org.ume.school.modules.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.ume.school.modules.model.enums.PlaySevenMode;
import org.ume.school.modules.model.enums.PlaySevenRewardType;
import org.ume.school.modules.model.enums.UserPlayStatus;

/**
 * 用户投注双色球
 * Created by Zz on 2018/10/25.
 */
@Entity
@Table(name = "uc_user_play_seven")
public class UserPlaySeven implements Serializable {
    
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
     * 投注类型  0单式 1复式
     */
    @Column(name = "mode", columnDefinition = "int(1) DEFAULT 0 COMMENT '投注类型  0单式 1复式'")
    private int mode;
    
    /**
     * 橘色选号
     */
    @Column(name = "six_number", columnDefinition = "varchar(128) NOT NULL COMMENT '橘色选号'")
    private String sixNumber;
    
    /**
     * 绿色选号
     */
    @Column(name = "one_number", columnDefinition = "varchar(64) NOT NULL COMMENT '绿色选号'")
    private String oneNumber;
    
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
     * 中奖奖项（几等奖）
     */
    @Column(name = "reward_code", columnDefinition = "int(4) DEFAULT 0 COMMENT '中奖奖项（几等奖）'")
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
    
    
    public UserPlaySeven() {
        
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

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public String getSixNumber() {
		return sixNumber;
	}

	public void setSixNumber(String sixNumber) {
		this.sixNumber = sixNumber;
	}

	public String getOneNumber() {
		return oneNumber;
	}

	public void setOneNumber(String oneNumber) {
		this.oneNumber = oneNumber;
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
        if(this.mode == PlaySevenMode.SINGLE.getValue())
            this.modeName = PlaySevenMode.SINGLE.getText();
        else if(this.mode == PlaySevenMode.MULTIPLE.getValue())
            this.modeName = PlaySevenMode.MULTIPLE.getText();
        else
            this.modeName = "未知类型";
        return this.modeName;
	}

	public void setModeName(String modeName) {
		this.modeName = modeName;
	}	

    public String getStatusName() {
        if(this.status == UserPlayStatus.NO.getValue())
            this.statusName = UserPlayStatus.NO.getText();
        else if(this.status == UserPlayStatus.YES.getValue())
            this.statusName = UserPlayStatus.YES.getText();
        else if(this.status == UserPlayStatus.WAIT.getValue())
            this.statusName = UserPlayStatus.WAIT.getText();
        else
            this.statusName = "未知状态";
        return this.statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }    

    public String getRewardCodeName() {    	
    	if(this.rewardCode == null)
    		this.rewardCodeName = "";
    	else if(this.rewardCode == PlaySevenRewardType.NO.getValue())
            this.rewardCodeName = PlaySevenRewardType.NO.getText();
        else if(this.rewardCode == PlaySevenRewardType.ONE.getValue())
            this.rewardCodeName = PlaySevenRewardType.ONE.getText();
        else if(this.rewardCode == PlaySevenRewardType.TOW.getValue())
            this.rewardCodeName = PlaySevenRewardType.TOW.getText();
        else if(this.rewardCode == PlaySevenRewardType.THREE.getValue())
            this.rewardCodeName = PlaySevenRewardType.THREE.getText();
        else if(this.rewardCode == PlaySevenRewardType.FOUR.getValue())
            this.rewardCodeName = PlaySevenRewardType.FOUR.getText();
        else if(this.rewardCode == PlaySevenRewardType.FIVE.getValue())
            this.rewardCodeName = PlaySevenRewardType.FIVE.getText();
        else if(this.rewardCode == PlaySevenRewardType.SIX.getValue())
            this.rewardCodeName = PlaySevenRewardType.SIX.getText();
        else
            this.rewardCodeName = "未知奖项";
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
