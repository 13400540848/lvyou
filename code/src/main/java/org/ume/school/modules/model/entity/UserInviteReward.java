package org.ume.school.modules.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.ume.school.modules.model.enums.UserInviteRewardStatus;

/**
 * 邀请奖励
 * Created by Zz on 2018/10/11.
 */
@Entity
@Table(name = "uc_user_invite_reward")
public class UserInviteReward implements Serializable {

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
     * 用户编号
     */
    @Column(name = "user_id", columnDefinition = "varchar(64) NOT NULL COMMENT '会员id'")
    private String userId;

    /**
     * 推荐者
     */
    @Column(name = "referrer_id", columnDefinition = "varchar(64) DEFAULT NULL COMMENT '推荐者id'")
    private String referrerId;

    /**
     * 奖励币种编号
     */
    @Column(name = "type_id", columnDefinition = "varchar(64) NOT NULL COMMENT '奖励币种编号'")
    private String typeId;

    /**
     * 奖励金额
     */
    @Column(name = "money", columnDefinition = "DOUBLE(10,2) DEFAULT 0 COMMENT '奖励金额'")
    private Double money;

    /**
     * 状态 0：待实名认证；1：已奖励
     */
    @Column(name = "status", columnDefinition = " tinyint(4) DEFAULT -1 COMMENT '状态 0：待实名认证；1：已奖励'")
    private Integer status = null;

    /**
     * 添加时间
     */
    @Column(name = "create_time", columnDefinition = "datetime DEFAULT NULL COMMENT '添加时间'")
    private Date createTime;

    @Transient
    private String userName;

    @Transient
    private String referrerName;

    @Transient
    private String typeName;

    @Transient
    private String statusName;

    public UserInviteReward() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReferrerId() {
        return referrerId;
    }

    public void setReferrerId(String referrerId) {
        this.referrerId = referrerId;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getReferrerName() {
        return referrerName;
    }

    public void setReferrerName(String referrerName) {
        this.referrerName = referrerName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getStatusName() {
        if (this.status == null)
            this.statusName = "";
        else if (this.status.intValue() == UserInviteRewardStatus.WAIT_CHECK.getValue())
            this.statusName = UserInviteRewardStatus.WAIT_CHECK.getText();
        else if (this.status.intValue() == UserInviteRewardStatus.SUCCESS.getValue())
            this.statusName = UserInviteRewardStatus.SUCCESS.getText();
        else
            this.statusName = "未知状态";
        return this.statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
