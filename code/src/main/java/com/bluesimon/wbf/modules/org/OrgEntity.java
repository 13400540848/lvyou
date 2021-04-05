package com.bluesimon.wbf.modules.org;

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
 * 组织部门
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "sys_org")
@Getter
@Setter
public class OrgEntity implements Serializable {

    @GeneratedValue(strategy = GenerationType.AUTO) 
    @Column(name = "id", columnDefinition = "int(11) NOT NULL COMMENT '主键id'")
    @Id
    private Long id;    

//    @Column(name = "name", columnDefinition = "varchar(100) DEFAULT NULL COMMENT '名称'")
//    private String name;
//    
//    @Column(name = "code", columnDefinition = " varchar(100) DEFAULT NULL COMMENT '编号'")
//    private String code;    
//
//    @Column(name = "url", columnDefinition = "varchar(500) DEFAULT NULL COMMENT '访问路径'")
//    private String url;
//
//    @Column(name = "icon", columnDefinition = "varchar(100) DEFAULT NULL COMMENT '图标'")
//    private String icon;
//    
//    @Column(name = "open_mode", columnDefinition = " tinyint(1) DEFAULT 0 COMMENT '打开方式，0 默认 1 弹出新页面'")
//    private Integer openMode;
//    
//    @Column(name = "is_hide", columnDefinition = " tinyint(1) DEFAULT 0 COMMENT '是否隐藏，1 是 0 否'")
//    private Integer isHide;
//    
//    @Column(name = "p_id", columnDefinition = " int(11) DEFAULT 0 COMMENT '上级菜单'")
//    private Integer pId;
//    
//    @Column(name = "type", columnDefinition = " tinyint(1) DEFAULT 0 COMMENT ' 类型，0 门户 1菜单'")
//    private Integer type;
//
//    @Column(name = "description", columnDefinition = " varchar(512) NULL DEFAULT NULL COMMENT '描述'")
//    private String description;
//
//    @Column(name = "sort", columnDefinition = "int(6) DEFAULT 0 COMMENT '序号'")
//    private Integer sort;
//    
//    @Column(name = "create_time", columnDefinition = " timestamp NULL DEFAULT NULL COMMENT '创建时间'")
//    private Date createTime;
}
