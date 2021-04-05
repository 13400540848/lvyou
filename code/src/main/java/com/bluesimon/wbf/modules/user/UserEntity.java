package com.bluesimon.wbf.modules.user;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

import com.bluesimon.wbf.IUser;

/**
 * 用户
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "sys_user")
@Getter
@Setter
public class UserEntity implements IUser, Serializable {

    @GeneratedValue(strategy = GenerationType.AUTO) 
    @Column(name = "id", columnDefinition = "int(11) NOT NULL COMMENT '主键id'")
    @Id
    private Long id;    

    @Column(name = "account", columnDefinition = "varchar(100) DEFAULT NULL COMMENT '登录账号'")
    private String account;
    
    @Column(name = "password", columnDefinition = " varchar(100) NULL DEFAULT NULL COMMENT '密码'")
    private String password;    

    @Column(name = "nick_name", columnDefinition = "varchar(64) DEFAULT NULL COMMENT '会员昵称'")
    private String nickName;

    @Column(name = "real_name", columnDefinition = "varchar(64) DEFAULT NULL COMMENT '真实姓名'")
    private String realName;
    
    @Column(name = "mail", columnDefinition = "varchar(128) DEFAULT NULL COMMENT '邮箱'")
    private String mail;
    
    @Column(name = "mobile_phone", columnDefinition = "varchar(20) DEFAULT NULL COMMENT '手机号码'")
    private String mobilePhone;
    
    @Column(name = "type", columnDefinition = " tinyint(4) DEFAULT 0 COMMENT ' 1、超级管理员（所有权限）；2、一般内部用户 ；  0: 外部注册用户 ; 3 外部关联用户(微信等)'")
    private Integer type;
    
    @Column(name = "status", columnDefinition = " tinyint(4) DEFAULT NULL COMMENT '删除标记0：可用；1、已删除；2、禁用'")
    private Integer status;
    
    @Column(name = "sex", columnDefinition = "tinyint(4) DEFAULT NULL COMMENT '0：保密；1.男，2女'")
    private Integer sex = 0;

    @Column(name = "head_image", columnDefinition = " varchar(128) NULL DEFAULT NULL COMMENT '头像'")
    private String headImage;

    @Column(name = "org_id", columnDefinition = "tinyint(4) DEFAULT NULL COMMENT '所属组织机构'")
    private Long orgId = 0L;

    @Column(name = "creator_id", columnDefinition = " varchar(512) DEFAULT NULL COMMENT '创建人'")
    private String creatorId;
    
    @Column(name = "create_time", columnDefinition = " timestamp NULL DEFAULT NULL COMMENT '创建时间'")
    private Date createTime;

    @Column(name = "update_time", columnDefinition = " timestamp NULL DEFAULT NULL COMMENT '更新时间'")
    private Date updateTime;
    
    @Transient
    private List<String> roles;
    
    @Transient
    private String smsCode;
    
    @Transient
    private String validateCode;


    @Override
    public Long getId() {
        // TODO Auto-generated method stub
        return id;
    }

    @Override
    public String getAccount() {
        // TODO Auto-generated method stub
        return account;
    }

    @Override
    public String getNickName() {
        // TODO Auto-generated method stub
        return nickName;
    }

    @Override
    public String getRealName() {
        // TODO Auto-generated method stub
        return realName;
    }

    @Override
    public List<String> getRole() {
        // TODO Auto-generated method stub
        return roles;
    }

    @Override
    public Integer getType() {
        // TODO Auto-generated method stub
        return type;
    }

}
