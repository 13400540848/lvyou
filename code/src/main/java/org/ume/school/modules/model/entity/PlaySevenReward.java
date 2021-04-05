package org.ume.school.modules.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.ume.school.modules.model.enums.PlaySevenRewardMode;
import org.ume.school.modules.model.enums.PlaySevenRewardType;

/**
 * 双色球奖项
 * Created by Zz on 2018/10/25.
 */
@Entity
@Table(name = "bs_play_seven_reward")
public class PlaySevenReward implements Serializable {
    
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
     * 奖项名称
     */
    @Column(name = "reward_name", columnDefinition = "varchar(64) NOT NULL COMMENT '奖项名称'")
    private String rewardName;
    
    /**
     * 奖项标识(1-6)
     */
    @Column(name = "reward_code", columnDefinition = "int(4) DEFAULT 0 COMMENT '奖项标识(1-6)'")
    private Integer rewardCode;
    
    /**
     * 奖励类型（0固定金额、1浮动金额）
     */
    @Column(name = "reward_mode", columnDefinition = "int(1) DEFAULT 0 COMMENT '奖励类型（0固定金额、1浮动金额）'")
    private Integer rewardMode;
    
    /**
     * 奖励倍数（固定金额）
     */
    @Column(name = "reward_times", columnDefinition = "DOUBLE(10,2) DEFAULT 0 COMMENT '奖励倍数（固定金额）'")
    private Double rewardTimes;
    
    /**
     * 浮动奖金百分比(浮动金额)
     */
    @Column(name = "reward_percent", columnDefinition = "DOUBLE(10,2) default 0 COMMENT '浮动奖金百分比(浮动金额)'")
    private Double rewardPercent;
    
    /**
     * 封顶金额(浮动金额)
     */
    @Column(name = "max_money", columnDefinition = "DOUBLE(10,2) default 0 COMMENT '封顶金额(浮动金额)'")
    private Double maxMoney;
    
    /**
     * 浮动最低要求奖池数量(浮动金额)
     */
    @Column(name = "require_money", columnDefinition = "DOUBLE(10,2) default 0 COMMENT '最低要求奖池数量(浮动金额)'")
    private Double requireMoney;
    
    /**
     * 添加时间
     */
    @Column(name = "create_time", columnDefinition = "datetime DEFAULT NULL COMMENT '添加时间'")
    private Date createTime;
    
    /**
     * 修改时间
     */
    @Column(name = "modify_time", columnDefinition = "datetime DEFAULT NULL COMMENT '修改时间'")
    private Date modifyTime;
        
    @Transient
    private String rewardModeName;
    @Transient
    private String rewardCodeName;
    
    public PlaySevenReward() {
        
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRewardName() {
		return rewardName;
	}

	public void setRewardName(String rewardName) {
		this.rewardName = rewardName;
	}

	public Integer getRewardCode() {
		return rewardCode;
	}

	public void setRewardCode(Integer rewardCode) {
		this.rewardCode = rewardCode;
	}

	public Integer getRewardMode() {
		return rewardMode;
	}

	public void setRewardMode(Integer rewardMode) {
		this.rewardMode = rewardMode;
	}

	public Double getRewardTimes() {
		return rewardTimes;
	}

	public void setRewardTimes(Double rewardTimes) {
		this.rewardTimes = rewardTimes;
	}

	public Double getRewardPercent() {
		return rewardPercent;
	}

	public void setRewardPercent(Double rewardPercent) {
		this.rewardPercent = rewardPercent;
	}

	public Double getMaxMoney() {
		return maxMoney;
	}

	public void setMaxMoney(Double maxMoney) {
		this.maxMoney = maxMoney;
	}

	public Double getRequireMoney() {
		return requireMoney;
	}

	public void setRequireMoney(Double requireMoney) {
		this.requireMoney = requireMoney;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getRewardModeName() {
	    if(this.rewardMode==null)
	        rewardModeName = "";
		for(PlaySevenRewardMode p : PlaySevenRewardMode.values()){
            if(p.getValue() == this.rewardMode.intValue()){
                this.rewardModeName = p.getText();
                break;
            }
        }
		return rewardModeName;
	}

	public void setRewardModeName(String rewardModeName) {
		this.rewardModeName = rewardModeName;
	}
	public String getRewardCodeName() {
        if(this.rewardCode==null)
            rewardCodeName = "";
        for(PlaySevenRewardType p : PlaySevenRewardType.values()){
            if(p.getValue() == this.rewardCode.intValue()){
                this.rewardCodeName = p.getText();
                break;
            }
        }
        return rewardCodeName;
    }

    public void setRewardCodeName(String rewardCodeName) {
        this.rewardCodeName = rewardCodeName;
    }
}
