package org.ume.school.modules.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.ume.school.modules.model.enums.PlaySevenRewardType;

/**
 * 快3抽奖结果详细（中奖情况）
 * Created by Zz on 2018/10/25.
 */
@Entity
@Table(name = "bs_play_three_result_reward")
public class PlayThreeResultReward implements Serializable {
    
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
     * 期数 20181031073
     */
    @Column(name = "play_time", columnDefinition = "DOUBLE(16,2) DEFAULT 0 COMMENT '期数 20181031073'")
    private Double playTime;
    
    /**
     * 奖项标识
     */
    @Column(name = "reward_code", columnDefinition = "int(4) DEFAULT 0 COMMENT '奖项标识'")
    private Integer rewardCode;
        
    /**
     * 注数
     */
    @Column(name = "count_number", columnDefinition = "int(11) DEFAULT 0 COMMENT '注数'")
    private Double countNumber;
    
    /**
     * 每注金额
     */
    @Column(name = "per_money", columnDefinition = "DOUBLE(10,2) DEFAULT 0 COMMENT '每注金额'")
    private Double perMoney;
    
    /**
     * 奖金
     */
    @Column(name = "reward_money", columnDefinition = "DOUBLE(10,2) DEFAULT 0 COMMENT '奖金'")
    private Double rewardMoney;
    
    /**
     * 添加时间
     */
    @Column(name = "create_time", columnDefinition = "datetime DEFAULT NULL COMMENT '添加时间'")
    private Date createTime;
    
    @Transient
    private String rewardCodeName;
    
    public PlayThreeResultReward() {
        
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getPlayTime() {
        return playTime;
    }

    public void setPlayTime(Double playTime) {
        this.playTime = playTime;
    }

    public Integer getRewardCode() {
        return rewardCode;
    }

    public void setRewardCode(int rewardCode) {
        this.rewardCode = rewardCode;
    }

    public Double getCountNumber() {
        return countNumber;
    }

    public void setCountNumber(Double countNumber) {
        this.countNumber = countNumber;
    }

    public Double getRewardMoney() {
        return rewardMoney;
    }

    public void setRewardMoney(Double rewardMoney) {
        this.rewardMoney = rewardMoney;
    }

    public Double getPerMoney() {
        return perMoney;
    }

    public void setPerMoney(Double perMoney) {
        this.perMoney = perMoney;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
    
}
