package org.ume.school.modules.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 项目发币
 * Created by Zz on 2018/10/14.
 */
@Entity
@Table(name = "bs_project_money_send")
public class ProjectMoneySend implements Serializable {
    
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
     * 项目编号
     */
    @Column(name = "project_id", columnDefinition = "varchar(64) NOT NULL COMMENT '项目编号'")
    private String projectId;
    
    /**
     * 发币金额
     */
    @Column(name = "all_money", columnDefinition = "DOUBLE(10,2) NOT NULL COMMENT '发币金额'")
    private Double allMoney;
    
    /**
     * 已发金额
     */
    @Column(name = "send_money", columnDefinition = "DOUBLE(10,2) NOT NULL COMMENT '已发金额'")
    private Double sendMoney;
    
    /**
     * 添加时间
     */
    @Column(name = "create_time", columnDefinition = "datetime DEFAULT NULL  COMMENT '添加时间'")
    private Date createTime;
    
    public ProjectMoneySend() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Double getAllMoney() {
        return allMoney;
    }

    public void setAllMoney(Double allMoney) {
        this.allMoney = allMoney;
    }

    public Double getSendMoney() {
        return sendMoney;
    }

    public void setSendMoney(Double sendMoney) {
        this.sendMoney = sendMoney;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
}
