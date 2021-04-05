package org.ume.school.modules.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.ume.school.modules.model.enums.PlayResultStatus;
import org.ume.school.modules.model.enums.PlayThreeMode;

/**
 * 快3抽奖结果
 * Created by Zz on 2018/11/1.
 */
@Entity
@Table(name = "bs_play_three_result")
public class PlayThreeResult implements Serializable {
    
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
     * 当天第几注
     */
    @Column(name = "day_index", columnDefinition = "int(11) DEFAULT 0 COMMENT '当天第几注'")
    private Integer dayIndex;
    
    /**
     * 状态（已开奖1、待开奖0）
     */
    @Column(name = "status", columnDefinition = "int(1) DEFAULT 0 COMMENT '状态（已开奖1、待开奖0）'")
    private Integer status;
    
    /**
     * 截止时间
     */
    @Column(name = "end_time", columnDefinition = "datetime DEFAULT NULL COMMENT '截止时间'")
    private Date endTime;
    
    /**
     * 开奖时间
     */
    @Column(name = "publish_time", columnDefinition = "datetime DEFAULT NULL COMMENT '开奖时间'")
    private Date publishTime;
    
    /**
     * 选号1
     */
    @Column(name = "number1", columnDefinition = "int(1) DEFAULT NULL COMMENT '选号1'")
    private Integer number1;
    
    /**
     * 选号2
     */
    @Column(name = "number2", columnDefinition = "int(1) DEFAULT NULL COMMENT '选号2'")
    private Integer number2;
    
    /**
     * 选号3
     */
    @Column(name = "number3", columnDefinition = "int(1) DEFAULT NULL COMMENT '选号3'")
    private Integer number3;
    
    /**
     * 选号形态（三同号、三不同号、二同号）
     */
    @Column(name = "mode", columnDefinition = "int(4) DEFAULT 0 COMMENT '选号形态（三同号、三不同号、二同号）'")
    private Integer mode;
    
    /**
     * 和值（3-18）
     */
    @Column(name = "sum_number", columnDefinition = "int(11) DEFAULT 0 COMMENT '和值（3-18）'")
    private Integer sumNumber;
    
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
     * 添加时间
     */
    @Column(name = "create_time", columnDefinition = "datetime DEFAULT NULL COMMENT '添加时间'")
    private Date createTime;
    
    /**
     * 中奖总金额
     */
    @Column(name = "reward_money", columnDefinition = "DOUBLE(10,2) DEFAULT 0 COMMENT '中奖总金额'")
    private Double rewardMoney;
    
    /**
     * 利润
     */
    @Column(name = "leave_money", columnDefinition = "DOUBLE(10,2) DEFAULT 0 COMMENT '利润'")
    private Double leaveMoney;
        
    @Transient
    private String statusName;
    @Transient
    private String modeName;
    
    @Transient
    private Date nowTime;
    
    @Transient
    private Double playTimeStart = null;
    @Transient
    private Double playTimeEnd = null;
    
    public PlayThreeResult() {
        
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

	public Double getPlayTimeStart() {
        return playTimeStart;
    }

    public void setPlayTimeStart(Double playTimeStart) {
        this.playTimeStart = playTimeStart;
    }

    public Double getPlayTimeEnd() {
        return playTimeEnd;
    }

    public void setPlayTimeEnd(Double playTimeEnd) {
        this.playTimeEnd = playTimeEnd;
    }

    public Integer getDayIndex() {
        return dayIndex;
    }

    public void setDayIndex(Integer dayIndex) {
        this.dayIndex = dayIndex;
    }

    public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public Integer getSumNumber() {
		return sumNumber;
	}

	public void setSumNumber(Integer sumNumber) {
		this.sumNumber = sumNumber;
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

	public Double getRewardMoney() {
        return rewardMoney;
    }

    public void setRewardMoney(Double rewardMoney) {
        this.rewardMoney = rewardMoney;
    }

    public Double getLeaveMoney() {
		return leaveMoney;
	}

	public void setLeaveMoney(Double leaveMoney) {
		this.leaveMoney = leaveMoney;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getStatusName() {
	    if(this.status!=null)
	        statusName = "";
	    for(PlayResultStatus p : PlayResultStatus.values()){
	        if(this.status.intValue() == p.getValue()){
	            this.statusName = p.getText();
	            break;
	        }
	    }
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
    
    public String getModeName() {
        modeName = "";
        if(this.mode!=null){
            for(PlayThreeMode p : PlayThreeMode.values()){
                if(this.mode.intValue() == p.getValue()){
                    this.modeName = p.getText();
                    break;
                }
            }
        }
        return modeName;
    }

    public void setModeName(String modeName) {
        this.modeName = modeName;
    }

    public Date getNowTime() {
        return nowTime;
    }

    public void setNowTime(Date nowTime) {
        this.nowTime = nowTime;
    }

    public Integer getNumber1() {
        return number1;
    }

    public void setNumber1(Integer number1) {
        this.number1 = number1;
    }

    public Integer getNumber2() {
        return number2;
    }

    public void setNumber2(Integer number2) {
        this.number2 = number2;
    }

    public Integer getNumber3() {
        return number3;
    }

    public void setNumber3(Integer number3) {
        this.number3 = number3;
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }
    
}
