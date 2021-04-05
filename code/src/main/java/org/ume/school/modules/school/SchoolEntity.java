package org.ume.school.modules.school;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 学校
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "bs_school")
@Getter
@Setter
public class SchoolEntity implements Serializable {

    @GeneratedValue(strategy = GenerationType.AUTO) 
    @Column(name = "id", columnDefinition = "int(11) NOT NULL COMMENT '主键id'")
    @Id
    private Long id;    

    @Column(name = "name", columnDefinition = "varchar(50) DEFAULT NULL COMMENT '名称'")
    private String name;
    
    @Column(name = "code", columnDefinition = " varchar(50) DEFAULT NULL COMMENT '编号'")
    private String code;   
    
      @Column(name = "status", columnDefinition = " tinyint(1) DEFAULT 0 COMMENT '状态，0正常 1禁用'")
      private Integer status;
    
    @Column(name = "creator_id", columnDefinition = " int(11) DEFAULT 0 COMMENT '创建人'")
    private Integer creatorId;
  
      @Column(name = "create_time", columnDefinition = " timestamp NULL DEFAULT NULL COMMENT '创建时间'")
      private Date createTime;
        
      @Column(name = "update_time", columnDefinition = " timestamp NULL DEFAULT NULL COMMENT '更新时间'")
      private Date updateTime;
      
      @Transient
      private boolean checked;
}
