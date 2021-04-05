package com.bluesimon.wbf.modules.dict.item;

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
 * 字典项
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "sys_dict_item")
@Getter
@Setter
public class DictItemEntity implements Serializable {

    @GeneratedValue(strategy = GenerationType.AUTO) 
    @Column(name = "id", columnDefinition = "int(11) NOT NULL COMMENT '主键id'")
    @Id
    private Long id;    

    @Column(name = "dict_id", columnDefinition = "int(11) DEFAULT NULL COMMENT '字典ID'")
    private Long dictId;
    
    @Column(name = "name", columnDefinition = "varchar(255) DEFAULT NULL COMMENT '名称'")
    private String name;
    
    @Column(name = "code", columnDefinition = " varchar(255) DEFAULT NULL COMMENT '编号'")
    private String code;  
    
    @Column(name = "value", columnDefinition = " varchar(1000) DEFAULT NULL COMMENT '值'")
    private String value;  
    
    @Column(name = "sort", columnDefinition = "int(6) DEFAULT NULL COMMENT '排序'")
    private Long sort;

    @Column(name = "remark", columnDefinition = " varchar(1000) NULL DEFAULT NULL COMMENT '备注'")
    private String remark;
    
    @Column(name = "create_time", columnDefinition = " timestamp NULL DEFAULT NULL COMMENT '创建时间'")
    private Date createTime;
    
    @Column(name = "update_time", columnDefinition = " timestamp NULL DEFAULT NULL COMMENT '更新时间'")
    private Date updateTime;
}
