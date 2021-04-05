package org.ume.school.modules.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 币种
 * Created by Zz on 2018/8/29.
 */
@Entity
@Table(name = "bs_money_type")
public class MoneyType implements Serializable {

    /**	
     * Member Description
     */

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @Column(name = "type_id", columnDefinition = "varchar(64) NOT NULL COMMENT '币种编号'")
    @Id
    private String typeId;

    /**
     * 币种名称
     */
    @Column(name = "type_name", columnDefinition = "varchar(255) DEFAULT NULL  COMMENT '币种名称'")
    private String typeName;

    /**
     * 币种类型：0代币 1投资币种 2系统
     */
    @Column(name = "type_mode", columnDefinition = "int(1) DEFAULT 0  COMMENT '币种类型：0代币 1投资币种 2系统'")
    private Integer typeMode;

    /**
     * 币种简称
     */
    @Column(name = "type_code", columnDefinition = "varchar(64) DEFAULT NULL  COMMENT '币种简称'")
    private String typeCode;

    /**
     * 币种充值地址
     */
    @Column(name = "recharge_address", columnDefinition = "varchar(255) DEFAULT NULL  COMMENT '币种充值地址'")
    private String rechargeAddress;

    /**
     * 币种二维码
     */
    @Column(name = "qr_code", columnDefinition = "varchar(255) DEFAULT NULL  COMMENT '币种二维码'")
    private String qrCode;

    /**
     * DG兑换比例
     */
    @Column(name = "dg_scale", columnDefinition = "DOUBLE(10,2) DEFAULT 0 COMMENT 'DG兑换比例'")
    private Double dgScale;
    
    /**
     * DG兑换手续费
     */
    @Column(name = "dg_broke_percent", columnDefinition = "DOUBLE(10,2) DEFAULT 0 COMMENT 'DG兑换手续费'")
    private Double dgBrokePercent;
    
    /**
     * 充值手续费百分比
     */
    @Column(name = "recharge_broke_percent", columnDefinition = "DOUBLE(10,2) DEFAULT 0 COMMENT '充值手续费百分比'")
    private Double rechargeBrokePercent;

    /**
     * 提现下限数量
     */
    @Column(name = "cash_min", columnDefinition = "DOUBLE(10,2) DEFAULT 1 COMMENT '提现下限数量'")
    private Double cashMin;

    /**
     * 提现手续费百分比
     */
    @Column(name = "cash_broke_percent", columnDefinition = "DOUBLE(10,2) DEFAULT 1 COMMENT '提现手续费百分比'")
    private Double cashBrokePercent;

    /**
     * 提现手续费最小值
     */
    @Column(name = "cash_broke_min", columnDefinition = "DOUBLE(18,8) DEFAULT 1 COMMENT '提现手续费最小值'")
    private Double cashBrokeMin;

    /**
     * 提现手续费上限
     */
    @Column(name = "cash_broke_max", columnDefinition = "DOUBLE(18,8) DEFAULT 1 COMMENT '提现手续费上限'")
    private Double cashBrokeMax;

    public MoneyType() {
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public Integer getTypeMode() {
        return typeMode;
    }

    public void setTypeMode(Integer typeMode) {
        this.typeMode = typeMode;
    }

    public String getRechargeAddress() {
        return rechargeAddress;
    }

    public void setRechargeAddress(String rechargeAddress) {
        this.rechargeAddress = rechargeAddress;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public Double getDgScale() {
        return dgScale;
    }

    public void setDgScale(Double dgScale) {
        this.dgScale = dgScale;
    }

    public Double getDgBrokePercent() {
        return dgBrokePercent;
    }

    public void setDgBrokePercent(Double dgBrokePercent) {
        this.dgBrokePercent = dgBrokePercent;
    }

    public Double getRechargeBrokePercent() {
        return rechargeBrokePercent;
    }

    public void setRechargeBrokePercent(Double rechargeBrokePercent) {
        this.rechargeBrokePercent = rechargeBrokePercent;
    }

    public Double getCashMin() {
        return cashMin;
    }

    public void setCashMin(Double cashMin) {
        this.cashMin = cashMin;
    }

    public Double getCashBrokePercent() {
        return cashBrokePercent;
    }

    public void setCashBrokePercent(Double cashBrokePercent) {
        this.cashBrokePercent = cashBrokePercent;
    }

    public Double getCashBrokeMin() {
        return cashBrokeMin;
    }

    public void setCashBrokeMin(Double cashBrokeMin) {
        this.cashBrokeMin = cashBrokeMin;
    }

    public Double getCashBrokeMax() {
        return cashBrokeMax;
    }

    public void setCashBrokeMax(Double cashBrokeMax) {
        this.cashBrokeMax = cashBrokeMax;
    }

}
