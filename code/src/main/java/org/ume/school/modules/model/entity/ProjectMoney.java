package org.ume.school.modules.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 项目币种设置
 * Created by Zz on 2018/9/1.
 */
@Entity
@Table(name = "bs_project_money")
public class ProjectMoney implements Serializable {
    
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
     * 目标金额
     */
    @Column(name = "all_money", columnDefinition = "DOUBLE(10,2) NOT NULL COMMENT '目标金额'")
    private Double allMoney;
    
    /**
     * 已投资金额
     */
    @Column(name = "money", columnDefinition = "DOUBLE(10,2) NOT NULL COMMENT '已投资金额'")
    private Double money;
    
    /**
     * 比例
     */
    @Column(name = "money_scale", columnDefinition = "DOUBLE(10,2) not null default 0 comment '比例'")
    private Double moneyScale;
    
    /**
     * 下限
     */
    @Column(name = "min", columnDefinition = "DOUBLE(10,2) not null default 1 comment '下限'")
    private Double min;
    
    /**
     * 上限
     */
    @Column(name = "max", columnDefinition = "DOUBLE(10,2) not null default 100 comment '上限'")
    private Double max;
    
    @Transient
    private String typeName;

    public ProjectMoney() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Double getAllMoney() {
        return allMoney;
    }

    public void setAllMoney(Double allMoney) {
        this.allMoney = allMoney;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Double getMoneyScale() {
        return moneyScale;
    }

    public void setMoneyScale(Double moneyScale) {
        this.moneyScale = moneyScale;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    
}
