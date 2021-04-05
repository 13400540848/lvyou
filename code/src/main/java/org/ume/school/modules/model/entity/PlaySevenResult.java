package org.ume.school.modules.model.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.ume.school.modules.model.enums.PlayResultStatus;

/**
 * 双色球抽奖结果
 * Created by Zz on 2018/10/25.
 */
@Entity
@Table(name = "bs_play_seven_result")
public class PlaySevenResult implements Serializable {
    
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
     * 橘色选号
     */
    @Column(name = "six_number", columnDefinition = "varchar(128) DEFAULT NULL COMMENT '橘色选号'")
    private String sixNumber;
    
    /**
     * 绿色选号
     */
    @Column(name = "one_number", columnDefinition = "varchar(64) DEFAULT NULL COMMENT '绿色选号'")
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
    private String publishTimeWeek;
    
    @Transient
    private Date nowTime;
    
    @Transient
    private Double playTimeStart = null;
    @Transient
    private Double playTimeEnd = null;
    
    public PlaySevenResult() {
        
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
		statusName = "";
		if(this.status == PlayResultStatus.FINISHED.getValue())
            this.statusName = PlayResultStatus.FINISHED.getText();
        else if(this.status == PlayResultStatus.WAIT.getValue())
            this.statusName = PlayResultStatus.WAIT.getText();
        else
            this.statusName = "未知状态";
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

    public String getPublishTimeWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.publishTime);
        int WeekOfYear = cal.get(Calendar.DAY_OF_WEEK);// 一周的第几天(1-7)
        WeekOfYear = (WeekOfYear == 0 ? 7 : (WeekOfYear - 1));
        publishTimeWeek = WeekOfYear+"";
        return publishTimeWeek;
    }

    public void setPublishTimeWeek(String publishTimeWeek) {
        this.publishTimeWeek = publishTimeWeek;
    }
    

    public Date getNowTime() {
        return nowTime;
    }

    public void setNowTime(Date nowTime) {
        this.nowTime = nowTime;
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
    
}
