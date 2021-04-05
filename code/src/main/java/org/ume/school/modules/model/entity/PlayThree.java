package org.ume.school.modules.model.entity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 快3
 * Created by Zz on 2018/11/1.
 */
@Entity
@Table(name = "bs_play_three")
public class PlayThree implements Serializable {
    
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
     * 名称
     */
    @Column(name = "play_name", columnDefinition = "varchar(64) NOT NULL COMMENT '名称'")
    private String playName;
    
    /**
     * 每注金额
     */
    @Column(name = "per_money", columnDefinition = "DOUBLE(10,2) NOT NULL DEFAULT 0 COMMENT '每注金额'")
    private Double perMoney;
    
    /**
     * 奖池
     */
    @Column(name = "all_money", columnDefinition = "DOUBLE(10,2) NOT NULL DEFAULT 0 COMMENT '奖池'")
    private Double allMoney;
    
    /**
     * 开奖频率（10分钟）
     */
    @Column(name = "play_rate", columnDefinition = "int(4) default 10 COMMENT '开奖频率（分钟）'")
    private Integer playRate;
    
    /**
     * 第一期开始时间
     */
    @Column(name = "start_time", columnDefinition = "varchar(64) COMMENT '第一期开始时间'")
    private String startTime;
    
    /**
     * 一天多少期
     */
    @Column(name = "count_time", columnDefinition = "int(11) DEFAULT 1 COMMENT '一天多少期'")
    private Integer countTime;
        
    /**
     * 截止时间 单位秒(120)
     */
    @Column(name = "end_time", columnDefinition = "int(11) DEFAULT 0 COMMENT '截止时间 单位秒(120)'")
    private int endTime;
        
    /**
     * 备注说明
     */
    @Column(name = "play_remark", columnDefinition = "varchar(255) DEFAULT NULL COMMENT '备注说明'")
    private String playRemark;
        
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
    
    /**
     * 一天结束时间
     */
    @Transient
    private String overTime="";
    
    /**
     * 今天已開數量
     */
    @Transient
    private int todayOpenCount=0;
    
    public PlayThree() {
        
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlayName() {
		return playName;
	}

	public void setPlayName(String playName) {
		this.playName = playName;
	}

	public Double getPerMoney() {
		return perMoney;
	}

	public void setPerMoney(Double perMoney) {
		this.perMoney = perMoney;
	}

	public Double getAllMoney() {
		return allMoney;
	}

	public void setAllMoney(Double allMoney) {
		this.allMoney = allMoney;
	}

	public Integer getPlayRate() {
		return playRate;
	}

	public void setPlayRate(Integer playRate) {
		this.playRate = playRate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public Integer getCountTime() {
		return countTime;
	}

	public void setCountTime(Integer countTime) {
		this.countTime = countTime;
	}

	public int getEndTime() {
		return endTime;
	}

	public void setEndTime(int endTime) {
		this.endTime = endTime;
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

    public String getPlayRemark() {
        return playRemark;
    }

    public void setPlayRemark(String playRemark) {
        this.playRemark = playRemark;
    }

    public String getOverTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate;
        try {
            startDate = sdf.parse(new SimpleDateFormat("yyyy-MM-dd ").format(new Date()) + this.startTime);
            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);
            cal.add(Calendar.MINUTE, this.playRate*this.countTime);
            this.overTime = new SimpleDateFormat("HH:mm:ss").format(cal.getTime());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return overTime;
    }

    public void setOverTime(String overTime) {
        this.overTime = overTime;
    }

    public int getTodayOpenCount() {
        return todayOpenCount;
    }

    public void setTodayOpenCount(int todayOpenCount) {
        this.todayOpenCount = todayOpenCount;
    }

}
