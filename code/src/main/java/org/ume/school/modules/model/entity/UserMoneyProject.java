package org.ume.school.modules.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.ume.school.modules.model.enums.UserMoneyProjectStatus;

/**
 * 用户投资项目
 * Created by Zz on 2018/9/3.
 */
@Entity
@Table(name = "uc_user_money_project")
public class UserMoneyProject implements Serializable {
    
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
     * 用户编号
     */
    @Column(name = "user_id", columnDefinition = "varchar(64) NOT NULL COMMENT '会员id'")
    private String userId;
    
    /**
     * 项目编号
     */
    @Column(name = "project_id", columnDefinition = "varchar(64) NOT NULL COMMENT '项目编号'")
    private String projectId;
    
    /**
     * 币种编号
     */
    @Column(name = "type_id", columnDefinition = "varchar(64) NOT NULL COMMENT '币种编号'")
    private String typeId;
    
    /**
     * 投资金额
     */
    @Column(name = "money", columnDefinition = "DOUBLE(18,8) DEFAULT 0 COMMENT '投资金额'")
    private Double money = (double)0;
    
    /**
     * 手续费用
     */
    @Column(name = "broke_money", columnDefinition = "DOUBLE(18,8) DEFAULT 0 COMMENT '投资金额'")
    private Double brokeMoney = (double)0;
    
    /**
     * 实际金额
     */
    @Column(name = "real_money", columnDefinition = "DOUBLE(18,8) DEFAULT 0 COMMENT '投资金额'")
    private Double realMoney = (double)0;
    
    /**
     * 项目币种金额
     */
    @Column(name = "project_money", columnDefinition = "DOUBLE(18,8) DEFAULT 0 COMMENT '项目币种金额'")
    private Double projectMoney = (double)0;
    
    /**
     * 发币金额
     */
    @Column(name = "send_money", columnDefinition = "DOUBLE(18,8) DEFAULT 0 COMMENT '发币金额'")
    private Double sendMoney = (double)0;
    
    /**
     * 发币编号
     */
    @Column(name = "send_id", columnDefinition = "varchar(64) default NULL COMMENT '发币编号'")
    private String sendId;
    
    /**
     * 发币时间
     */
    @Column(name = "send_time", columnDefinition = "datetime DEFAULT NULL comment '发币时间'")
    private Date sendTime;
    
    /**
     * 投资时间
     */
    @Column(name = "create_time", columnDefinition = "datetime DEFAULT NULL comment '投资时间'")
    private Date createTime;
    
    /**
     * 状态 0：正常；1、已发币；
     */
    @Column(name = "status", columnDefinition = " tinyint(4) DEFAULT 0 COMMENT '状态 0：正常；1、已发币；'")
    private Integer status = 0;
    
    @Transient
    private String userName;
    @Transient
    private String projectName;
    @Transient
    private String typeName;
    @Transient
    private String statusName;    
    @Transient
    private Project project;
    @Transient
    private String validateCode;
    @Transient
    private String dealPassword;
        
    public Double getBrokeMoney() {
        return brokeMoney;
    }

    public void setBrokeMoney(Double brokeMoney) {
        this.brokeMoney = brokeMoney;
    }

    public Double getRealMoney() {
        return realMoney;
    }

    public void setRealMoney(Double realMoney) {
        this.realMoney = realMoney;
    }

    public Double getProjectMoney() {
        return projectMoney;
    }

    public void setProjectMoney(Double projectMoney) {
        this.projectMoney = projectMoney;
    }

    public Double getSendMoney() {
        return sendMoney;
    }

    public void setSendMoney(Double sendMoney) {
        this.sendMoney = sendMoney;
    }
    
    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public UserMoneyProject() {
        
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getStatusName() {
        if(this.statusName==null)
            this.statusName = "";
        if(this.status.intValue() == UserMoneyProjectStatus.NORMAL.getValue())
            this.statusName = UserMoneyProjectStatus.NORMAL.getText();
        else if(this.status.intValue() == UserMoneyProjectStatus.SEND_FINISHED.getValue())
            this.statusName = UserMoneyProjectStatus.SEND_FINISHED.getText();
        else if(this.status.intValue() == UserMoneyProjectStatus.SEND_PART.getValue())
            this.statusName = UserMoneyProjectStatus.SEND_PART.getText();
        else
            this.statusName = "未知状态";
        return this.statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public String getDealPassword() {
        return dealPassword;
    }

    public void setDealPassword(String dealPassword) {
        this.dealPassword = dealPassword;
    }

    
}
