package org.ume.school.modules.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 系统设置
 * Created by Zz on 2018/10/13.
 */
@Entity
@Table(name = "bs_config")
public class Config implements Serializable {
    
    /**	
     * Member Description
     */
    
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @Column(name = "id", columnDefinition = "varchar(64) NOT NULL")
    @Id
    private String id;
    
    /**
     * 标题
     */
    @Column(name = "title", columnDefinition = "varchar(255) DEFAULT NULL")
    private String title;

    /**
     * 说明
     */
    @Column(name = "description", columnDefinition = "varchar(512) DEFAULT NULL")
    private String description;
    
    /**
     * 配置编码
     */
    @Column(name = "code", columnDefinition = "varchar(64) DEFAULT NULL COMMENT '配置编码'")
    private String code;
    
    /**
     * 配置值
     */
    @Column(name = "value", columnDefinition = "varchar(255) DEFAULT NULL COMMENT '配置值'")
    private String value;
    
    public Config() {
        
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
}
