package com.bluesimon.wbf.modules.dict;

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
 * 字典
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "sys_dict")
@Getter
@Setter
public class DictEntity implements Serializable {

    @GeneratedValue(strategy = GenerationType.AUTO) 
    @Column(name = "id", columnDefinition = "int(11) NOT NULL COMMENT '主键id'")
    @Id
    private Long id;    

    @Column(name = "name", columnDefinition = "varchar(100) DEFAULT NULL COMMENT '名称'")
    private String name;
    
    @Column(name = "code", columnDefinition = " varchar(100) DEFAULT NULL COMMENT '编号'")
    private String code;    

    @Column(name = "remark", columnDefinition = " varchar(255) NULL DEFAULT NULL COMMENT '备注'")
    private String remark;
    
    @Column(name = "create_time", columnDefinition = " timestamp NULL DEFAULT NULL COMMENT '创建时间'")
    private Date createTime;
    
    @Column(name = "update_time", columnDefinition = " timestamp NULL DEFAULT NULL COMMENT '更新时间'")
    private Date updateTime;
}
