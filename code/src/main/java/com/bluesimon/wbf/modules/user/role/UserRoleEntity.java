package com.bluesimon.wbf.modules.user.role;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户角色
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "sys_role_user")
@Getter
@Setter
public class UserRoleEntity implements Serializable {

    @GeneratedValue(strategy = GenerationType.AUTO) 
    @Column(name = "id", columnDefinition = "int(11) NOT NULL COMMENT '主键id'")
    @Id
    private Long id;    
    
    @Column(name = "user_id", columnDefinition = "int(11) DEFAULT 0 COMMENT '用户ID'")
    private Long userId;

    @Column(name = "role_id", columnDefinition = "int(11) DEFAULT 0 COMMENT '角色ID'")
    private Long roleId;
  
      @Column(name = "create_time", columnDefinition = " timestamp NULL DEFAULT NULL COMMENT '创建时间'")
      private Date createTime;
}
