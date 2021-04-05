package com.bluesimon.wbf.modules.role.menu;

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
 * 角色菜单
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "sys_role_menu")
@Getter
@Setter
public class RoleMenuEntity implements Serializable {

    @GeneratedValue(strategy = GenerationType.AUTO) 
    @Column(name = "id", columnDefinition = "int(11) NOT NULL COMMENT '主键id'")
    @Id
    private Long id;    
    
    @Column(name = "menu_id", columnDefinition = "int(11) DEFAULT 0 COMMENT '菜单ID'")
    private Long menuId;

    @Column(name = "role_id", columnDefinition = "int(11) DEFAULT 0 COMMENT '角色ID'")
    private Long roleId;
  
      @Column(name = "create_time", columnDefinition = " timestamp NULL DEFAULT NULL COMMENT '创建时间'")
      private Date createTime;
}
