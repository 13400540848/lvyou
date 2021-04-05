package org.ume.school.modules.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 快3走势图
 * Created by Zz on 2018/11/1.
 */
@Entity
@Table(name = "bs_play_three_result_log")
public class PlayThreeResultLog implements Serializable {
    
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
     * 期数 20181031073
     */
    @Column(name = "play_time", columnDefinition = "DOUBLE(16,2) DEFAULT 0 COMMENT '期数 20181031073'")
    private Double playTime;
    
    /**
     * 开奖号码1
     */
    @Column(name = "number1", columnDefinition = "int(1) DEFAULT 0 COMMENT '开奖号码1'")
    private int number1 = 0;
    /**
     * 开奖号码2
     */
    @Column(name = "number2", columnDefinition = "int(1) DEFAULT 0 COMMENT '开奖号码2'")
    private int number2 = 0;
    /**
     * 开奖号码3
     */
    @Column(name = "number3", columnDefinition = "int(1) DEFAULT 0 COMMENT '开奖号码3'")
    private int number3 = 0;
    
    /**
     * 选号1（累计未中次数）
     */
    @Column(name = "n1", columnDefinition = "int(11) DEFAULT 0 COMMENT '选号1（累计未中次数）'")
    private int n1 = 0;
    
    /**
     * 选号2（累计未中次数）
     */
    @Column(name = "n2", columnDefinition = "int(11) DEFAULT 0 COMMENT '选号2（累计未中次数）'")
    private int n2 = 0;
    
    /**
     * 选号3（累计未中次数）
     */
    @Column(name = "n3", columnDefinition = "int(11) DEFAULT 0 COMMENT '选号3（累计未中次数）'")
    private int n3 = 0;
    
    /**
     * 选号4（累计未中次数）
     */
    @Column(name = "n4", columnDefinition = "int(11) DEFAULT 0 COMMENT '选号4（累计未中次数）'")
    private int n4 = 0;
    
    /**
     * 选号5（累计未中次数）
     */
    @Column(name = "n5", columnDefinition = "int(11) DEFAULT 0 COMMENT '选号5（累计未中次数）'")
    private int n5 = 0;
    
    /**
     * 选号6（累计未中次数）
     */
    @Column(name = "n6", columnDefinition = "int(11) DEFAULT 0 COMMENT '选号6（累计未中次数）'")
    private int n6 = 0;
    
    /**
     * 和值3（累计未中次数）
     */
    @Column(name = "sum3", columnDefinition = "int(11) DEFAULT 0 COMMENT '和值3（累计未中次数）'")
    private int sum3 = 0;
    
    /**
     * 和值4（累计未中次数）
     */
    @Column(name = "sum4", columnDefinition = "int(11) DEFAULT 0 COMMENT '和值4（累计未中次数）'")
    private int sum4 = 0;
    
    /**
     * 和值5（累计未中次数）
     */
    @Column(name = "sum5", columnDefinition = "int(11) DEFAULT 0 COMMENT '和值5（累计未中次数）'")
    private int sum5 = 0;
    
    /**
     * 和值6（累计未中次数）
     */
    @Column(name = "sum6", columnDefinition = "int(11) DEFAULT 0 COMMENT '和值6（累计未中次数）'")
    private int sum6 = 0;
    
    /**
     * 和值7（累计未中次数）
     */
    @Column(name = "sum7", columnDefinition = "int(11) DEFAULT 0 COMMENT '和值7（累计未中次数）'")
    private int sum7 = 0;
    
    /**
     * 和值8（累计未中次数）
     */
    @Column(name = "sum8", columnDefinition = "int(11) DEFAULT 0 COMMENT '和值8（累计未中次数）'")
    private int sum8 = 0;
    
    /**
     * 和值9（累计未中次数）
     */
    @Column(name = "sum9", columnDefinition = "int(11) DEFAULT 0 COMMENT '和值9（累计未中次数）'")
    private int sum9 = 0;
    
    /**
     * 和值10（累计未中次数）
     */
    @Column(name = "sum10", columnDefinition = "int(11) DEFAULT 0 COMMENT '和值10（累计未中次数）'")
    private int sum10 = 0;
    
    /**
     * 和值11（累计未中次数）
     */
    @Column(name = "sum11", columnDefinition = "int(11) DEFAULT 0 COMMENT '和值11（累计未中次数）'")
    private int sum11 = 0;
    
    /**
     * 和值12（累计未中次数）
     */
    @Column(name = "sum12", columnDefinition = "int(11) DEFAULT 0 COMMENT '和值12（累计未中次数）'")
    private int sum12 = 0;
    
    /**
     * 和值13（累计未中次数）
     */
    @Column(name = "sum13", columnDefinition = "int(11) DEFAULT 0 COMMENT '和值13（累计未中次数）'")
    private int sum13 = 0;
    
    /**
     * 和值14（累计未中次数）
     */
    @Column(name = "sum14", columnDefinition = "int(11) DEFAULT 0 COMMENT '和值14（累计未中次数）'")
    private int sum14 = 0;
    
    /**
     * 和值15（累计未中次数）
     */
    @Column(name = "sum15", columnDefinition = "int(11) DEFAULT 0 COMMENT '和值15（累计未中次数）'")
    private int sum15 = 0;
    
    /**
     * 和值16（累计未中次数）
     */
    @Column(name = "sum16", columnDefinition = "int(11) DEFAULT 0 COMMENT '和值16（累计未中次数）'")
    private int sum16 = 0;
    
    /**
     * 和值17（累计未中次数）
     */
    @Column(name = "sum17", columnDefinition = "int(11) DEFAULT 0 COMMENT '和值17（累计未中次数）'")
    private int sum17 = 0;
    
    /**
     * 和值18（累计未中次数）
     */
    @Column(name = "sum18", columnDefinition = "int(11) DEFAULT 0 COMMENT '和值18（累计未中次数）'")
    private int sum18 = 0;
    
    /**
     * 添加时间
     */
    @Column(name = "create_time", columnDefinition = "datetime DEFAULT NULL COMMENT '添加时间'")
    private Date createTime;
    
    @Transient
    private Double playTimeStart = null;
    @Transient
    private Double playTimeEnd = null;
    
    public PlayThreeResultLog() {
        
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public Double getPlayTime() {
        return playTime;
    }


    public void setPlayTime(Double playTime) {
        this.playTime = playTime;
    }

    public Double getPlayTimeStart() {
        return playTimeStart;
    }


    public void setPlayTimeStart(Double playTimeStart) {
        this.playTimeStart = playTimeStart;
    }


    public Double getPlayTimeEnd() {
        return playTimeEnd;
    }


    public void setPlayTimeEnd(Double playTimeEnd) {
        this.playTimeEnd = playTimeEnd;
    }


    public int getNumber1() {
        return number1;
    }


    public void setNumber1(int number1) {
        this.number1 = number1;
    }


    public int getNumber2() {
        return number2;
    }


    public void setNumber2(int number2) {
        this.number2 = number2;
    }


    public int getNumber3() {
        return number3;
    }


    public void setNumber3(int number3) {
        this.number3 = number3;
    }


    public int getN1() {
        return n1;
    }


    public void setN1(int n1) {
        this.n1 = n1;
    }


    public int getN2() {
        return n2;
    }


    public void setN2(int n2) {
        this.n2 = n2;
    }


    public int getN3() {
        return n3;
    }


    public void setN3(int n3) {
        this.n3 = n3;
    }


    public int getN4() {
        return n4;
    }


    public void setN4(int n4) {
        this.n4 = n4;
    }


    public int getN5() {
        return n5;
    }


    public void setN5(int n5) {
        this.n5 = n5;
    }


    public int getN6() {
        return n6;
    }


    public void setN6(int n6) {
        this.n6 = n6;
    }


    public int getSum3() {
        return sum3;
    }


    public void setSum3(int sum3) {
        this.sum3 = sum3;
    }


    public int getSum4() {
        return sum4;
    }


    public void setSum4(int sum4) {
        this.sum4 = sum4;
    }


    public int getSum5() {
        return sum5;
    }


    public void setSum5(int sum5) {
        this.sum5 = sum5;
    }


    public int getSum6() {
        return sum6;
    }


    public void setSum6(int sum6) {
        this.sum6 = sum6;
    }


    public int getSum7() {
        return sum7;
    }


    public void setSum7(int sum7) {
        this.sum7 = sum7;
    }


    public int getSum8() {
        return sum8;
    }


    public void setSum8(int sum8) {
        this.sum8 = sum8;
    }


    public int getSum9() {
        return sum9;
    }


    public void setSum9(int sum9) {
        this.sum9 = sum9;
    }


    public int getSum10() {
        return sum10;
    }


    public void setSum10(int sum10) {
        this.sum10 = sum10;
    }


    public int getSum11() {
        return sum11;
    }


    public void setSum11(int sum11) {
        this.sum11 = sum11;
    }


    public int getSum12() {
        return sum12;
    }


    public void setSum12(int sum12) {
        this.sum12 = sum12;
    }


    public int getSum13() {
        return sum13;
    }


    public void setSum13(int sum13) {
        this.sum13 = sum13;
    }


    public int getSum14() {
        return sum14;
    }


    public void setSum14(int sum14) {
        this.sum14 = sum14;
    }


    public int getSum15() {
        return sum15;
    }


    public void setSum15(int sum15) {
        this.sum15 = sum15;
    }


    public int getSum16() {
        return sum16;
    }


    public void setSum16(int sum16) {
        this.sum16 = sum16;
    }


    public int getSum17() {
        return sum17;
    }


    public void setSum17(int sum17) {
        this.sum17 = sum17;
    }


    public int getSum18() {
        return sum18;
    }


    public void setSum18(int sum18) {
        this.sum18 = sum18;
    }


    public Date getCreateTime() {
        return createTime;
    }


    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
