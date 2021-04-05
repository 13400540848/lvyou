package org.ume.school.modules.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.ume.school.modules.model.enums.PlayThreeRewardType;

/**
 * 快3奖项
 * Created by Zz on 2018/11/1.
 */
@Entity
@Table(name = "bs_play_three_reward")
public class PlayThreeReward implements Serializable {
    
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
     * 奖项标识(3……)
     */
    @Column(name = "reward_code", columnDefinition = "int(4) DEFAULT 0 COMMENT '奖项标识(3……)'")
    private Integer rewardCode;
    
    /**
     * 奖励倍数（固定金额）
     */
    @Column(name = "reward_times", columnDefinition = "DOUBLE(10,2) DEFAULT 0 COMMENT '奖励倍数（固定金额）'")
    private Double rewardTimes;
    
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
    private String rewardCodeName;
    
    public PlayThreeReward() {
        
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

	public Double getRewardTimes() {
		return rewardTimes;
	}

	public void setRewardTimes(Double rewardTimes) {
		this.rewardTimes = rewardTimes;
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
	public String getRewardCodeName() {        
        if(this.rewardCode == null)
            this.rewardCodeName = "";
        for(PlayThreeRewardType p : PlayThreeRewardType.values()){
            if(p.getValue() == this.rewardCode.intValue()){
                this.rewardCodeName = p.getText();
                break;
            }
        }
        return this.rewardCodeName;
    }

    public void setRewardCodeName(String rewardCodeName) {
        this.rewardCodeName = rewardCodeName;
    }
	//PlayThreeRewardType
}
