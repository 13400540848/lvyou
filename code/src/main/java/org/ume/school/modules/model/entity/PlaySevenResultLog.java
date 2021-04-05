package org.ume.school.modules.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 双色球走势图
 * Created by Zz on 2018/10/25.
 */
@Entity
@Table(name = "bs_play_seven_result_log")
public class PlaySevenResultLog implements Serializable {
    
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
     * 六球1（累计未中次数）
     */
    @Column(name = "six1", columnDefinition = "int(11) DEFAULT 0 COMMENT '六球1（累计未中次数）'")
    private int six1 = 0;
    
    /**
     * 六球2（累计未中次数）
     */
    @Column(name = "six2", columnDefinition = "int(11) DEFAULT 0 COMMENT '六球2（累计未中次数）'")
    private int six2 = 0;
    
    /**
     * 六球3（累计未中次数）
     */
    @Column(name = "six3", columnDefinition = "int(11) DEFAULT 0 COMMENT '六球3（累计未中次数）'")
    private int six3 = 0;
    
    /**
     * 六球4（累计未中次数）
     */
    @Column(name = "six4", columnDefinition = "int(11) DEFAULT 0 COMMENT '六球4（累计未中次数）'")
    private int six4 = 0;
    
    /**
     * 六球5（累计未中次数）
     */
    @Column(name = "six5", columnDefinition = "int(11) DEFAULT 0 COMMENT '六球5（累计未中次数）'")
    private int six5 = 0;
    
    /**
     * 六球6（累计未中次数）
     */
    @Column(name = "six6", columnDefinition = "int(11) DEFAULT 0 COMMENT '六球6（累计未中次数）'")
    private int six6 = 0;
    
    /**
     * 六球7（累计未中次数）
     */
    @Column(name = "six7", columnDefinition = "int(11) DEFAULT 0 COMMENT '六球7（累计未中次数）'")
    private int six7 = 0;
    
    /**
     * 六球8（累计未中次数）
     */
    @Column(name = "six8", columnDefinition = "int(11) DEFAULT 0 COMMENT '六球8（累计未中次数）'")
    private int six8 = 0;
    
    /**
     * 六球9（累计未中次数）
     */
    @Column(name = "six9", columnDefinition = "int(11) DEFAULT 0 COMMENT '六球9（累计未中次数）'")
    private int six9 = 0;
    
    /**
     * 六球10（累计未中次数）
     */
    @Column(name = "six10", columnDefinition = "int(11) DEFAULT 0 COMMENT '六球10（累计未中次数）'")
    private int six10 = 0;
    
    /**
     * 六球11（累计未中次数）
     */
    @Column(name = "six11", columnDefinition = "int(11) DEFAULT 0 COMMENT '六球11（累计未中次数）'")
    private int six11 = 0;
    
    /**
     * 六球12（累计未中次数）
     */
    @Column(name = "six12", columnDefinition = "int(11) DEFAULT 0 COMMENT '六球12（累计未中次数）'")
    private int six12 = 0;
    
    /**
     * 六球13（累计未中次数）
     */
    @Column(name = "six13", columnDefinition = "int(11) DEFAULT 0 COMMENT '六球13（累计未中次数）'")
    private int six13 = 0;
    
    /**
     * 六球14（累计未中次数）
     */
    @Column(name = "six14", columnDefinition = "int(11) DEFAULT 0 COMMENT '六球14（累计未中次数）'")
    private int six14 = 0;
    
    /**
     * 六球15（累计未中次数）
     */
    @Column(name = "six15", columnDefinition = "int(11) DEFAULT 0 COMMENT '六球15（累计未中次数）'")
    private int six15 = 0;
    
    /**
     * 六球16（累计未中次数）
     */
    @Column(name = "six16", columnDefinition = "int(11) DEFAULT 0 COMMENT '六球16（累计未中次数）'")
    private int six16 = 0;
    
    /**
     * 六球17（累计未中次数）
     */
    @Column(name = "six17", columnDefinition = "int(11) DEFAULT 0 COMMENT '六球17（累计未中次数）'")
    private int six17 = 0;
    
    /**
     * 六球8（累计未中次数）
     */
    @Column(name = "six18", columnDefinition = "int(11) DEFAULT 0 COMMENT '六球18（累计未中次数）'")
    private int six18 = 0;
    
    /**
     * 六球19（累计未中次数）
     */
    @Column(name = "six19", columnDefinition = "int(11) DEFAULT 0 COMMENT '六球19（累计未中次数）'")
    private int six19 = 0;
    
    /**
     * 六球20（累计未中次数）
     */
    @Column(name = "six20", columnDefinition = "int(11) DEFAULT 0 COMMENT '六球20（累计未中次数）'")
    private int six20 = 0;
    
    /**
     * 六球21（累计未中次数）
     */
    @Column(name = "six21", columnDefinition = "int(11) DEFAULT 0 COMMENT '六球21（累计未中次数）'")
    private int six21 = 0;
    
    /**
     * 六球22（累计未中次数）
     */
    @Column(name = "six22", columnDefinition = "int(11) DEFAULT 0 COMMENT '六球22（累计未中次数）'")
    private int six22 = 0;
    
    /**
     * 六球23（累计未中次数）
     */
    @Column(name = "six23", columnDefinition = "int(11) DEFAULT 0 COMMENT '六球23（累计未中次数）'")
    private int six23 = 0;
    
    /**
     * 六球24（累计未中次数）
     */
    @Column(name = "six24", columnDefinition = "int(11) DEFAULT 0 COMMENT '六球24（累计未中次数）'")
    private int six24 = 0;
    
    /**
     * 六球25（累计未中次数）
     */
    @Column(name = "six25", columnDefinition = "int(11) DEFAULT 0 COMMENT '六球15（累计未中次数）'")
    private int six25 = 0;
    
    /**
     * 六球26（累计未中次数）
     */
    @Column(name = "six26", columnDefinition = "int(11) DEFAULT 0 COMMENT '六球26（累计未中次数）'")
    private int six26 = 0;
    
    /**
     * 六球27（累计未中次数）
     */
    @Column(name = "six27", columnDefinition = "int(11) DEFAULT 0 COMMENT '六球27（累计未中次数）'")
    private int six27 = 0;
    
    /**
     * 六球28（累计未中次数）
     */
    @Column(name = "six28", columnDefinition = "int(11) DEFAULT 0 COMMENT '六球28（累计未中次数）'")
    private int six28 = 0;
    
    /**
     * 六球29（累计未中次数）
     */
    @Column(name = "six29", columnDefinition = "int(11) DEFAULT 0 COMMENT '六球29（累计未中次数）'")
    private int six29 = 0;
    
    /**
     * 六球30（累计未中次数）
     */
    @Column(name = "six30", columnDefinition = "int(11) DEFAULT 0 COMMENT '六球30（累计未中次数）'")
    private int six30 = 0;
    
    /**
     * 六球31（累计未中次数）
     */
    @Column(name = "six31", columnDefinition = "int(11) DEFAULT 0 COMMENT '六球31（累计未中次数）'")
    private int six31 = 0;
    
    /**
     * 六球32（累计未中次数）
     */
    @Column(name = "six32", columnDefinition = "int(11) DEFAULT 0 COMMENT '六球32（累计未中次数）'")
    private int six32 = 0;
    
    /**
     * 六球33（累计未中次数）
     */
    @Column(name = "six33", columnDefinition = "int(11) DEFAULT 0 COMMENT '六球33（累计未中次数）'")
    private int six33 = 0;
    
    /**
     * 一球1（累计未中次数）
     */
    @Column(name = "one1", columnDefinition = "int(11) DEFAULT 0 COMMENT '一球1（累计未中次数）'")
    private int one1 = 0;
    
    /**
     * 一球2（累计未中次数）
     */
    @Column(name = "one2", columnDefinition = "int(11) DEFAULT 0 COMMENT '一球2（累计未中次数）'")
    private int one2 = 0;
    
    /**
     * 一球3（累计未中次数）
     */
    @Column(name = "one3", columnDefinition = "int(11) DEFAULT 0 COMMENT '一球3（累计未中次数）'")
    private int one3 = 0;
    
    /**
     * 一球4（累计未中次数）
     */
    @Column(name = "one4", columnDefinition = "int(11) DEFAULT 0 COMMENT '一球4（累计未中次数）'")
    private int one4= 0;
    
    /**
     * 一球5（累计未中次数）
     */
    @Column(name = "one5", columnDefinition = "int(11) DEFAULT 0 COMMENT '一球5（累计未中次数）'")
    private int one5 = 0;
    
    /**
     * 一球6（累计未中次数）
     */
    @Column(name = "one6", columnDefinition = "int(11) DEFAULT 0 COMMENT '一球6（累计未中次数）'")
    private int one6 = 0;
    
    /**
     * 一球7（累计未中次数）
     */
    @Column(name = "one7", columnDefinition = "int(11) DEFAULT 0 COMMENT '一球7（累计未中次数）'")
    private int one7 = 0;
    
    /**
     * 一球8（累计未中次数）
     */
    @Column(name = "one8", columnDefinition = "int(11) DEFAULT 0 COMMENT '一球8（累计未中次数）'")
    private int one8 = 0;
    
    /**
     * 一球9（累计未中次数）
     */
    @Column(name = "one9", columnDefinition = "int(11) DEFAULT 0 COMMENT '一球9（累计未中次数）'")
    private int one9 = 0;
    
    /**
     * 一球10（累计未中次数）
     */
    @Column(name = "one10", columnDefinition = "int(11) DEFAULT 0 COMMENT '一球10（累计未中次数）'")
    private int one10 = 0;
    
    /**
     * 一球11（累计未中次数）
     */
    @Column(name = "one11", columnDefinition = "int(11) DEFAULT 0 COMMENT '一球11（累计未中次数）'")
    private int one11 = 0;
    
    /**
     * 一球12（累计未中次数）
     */
    @Column(name = "one12", columnDefinition = "int(11) DEFAULT 0 COMMENT '一球12（累计未中次数）'")
    private int one12 = 0;
    
    /**
     * 一球13（累计未中次数）
     */
    @Column(name = "one13", columnDefinition = "int(11) DEFAULT 0 COMMENT '一球13（累计未中次数）'")
    private int one13 = 0;
    
    /**
     * 一球14（累计未中次数）
     */
    @Column(name = "one14", columnDefinition = "int(11) DEFAULT 0 COMMENT '一球14（累计未中次数）'")
    private int one14= 0;
    
    /**
     * 一球15（累计未中次数）
     */
    @Column(name = "one15", columnDefinition = "int(11) DEFAULT 0 COMMENT '一球15（累计未中次数）'")
    private int one15 = 0;
    
    /**
     * 一球16（累计未中次数）
     */
    @Column(name = "one16", columnDefinition = "int(11) DEFAULT 0 COMMENT '一球16（累计未中次数）'")
    private int one16 = 0;
    
    /**
     * 添加时间
     */
    @Column(name = "create_time", columnDefinition = "datetime DEFAULT NULL COMMENT '添加时间'")
    private Date createTime;
    
    @Transient
    private Double playTimeStart = null;
    @Transient
    private Double playTimeEnd = null;
    
    public PlaySevenResultLog() {
        
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


    public int getSix1() {
        return six1;
    }


    public void setSix1(int six1) {
        this.six1 = six1;
    }


    public int getSix2() {
        return six2;
    }


    public void setSix2(int six2) {
        this.six2 = six2;
    }


    public int getSix3() {
        return six3;
    }


    public void setSix3(int six3) {
        this.six3 = six3;
    }


    public int getSix4() {
        return six4;
    }


    public void setSix4(int six4) {
        this.six4 = six4;
    }


    public int getSix5() {
        return six5;
    }


    public void setSix5(int six5) {
        this.six5 = six5;
    }


    public int getSix6() {
        return six6;
    }


    public void setSix6(int six6) {
        this.six6 = six6;
    }


    public int getSix7() {
        return six7;
    }


    public void setSix7(int six7) {
        this.six7 = six7;
    }


    public int getSix8() {
        return six8;
    }


    public void setSix8(int six8) {
        this.six8 = six8;
    }


    public int getSix9() {
        return six9;
    }


    public void setSix9(int six9) {
        this.six9 = six9;
    }


    public int getSix10() {
        return six10;
    }


    public void setSix10(int six10) {
        this.six10 = six10;
    }


    public int getSix11() {
        return six11;
    }


    public void setSix11(int six11) {
        this.six11 = six11;
    }


    public int getSix12() {
        return six12;
    }


    public void setSix12(int six12) {
        this.six12 = six12;
    }


    public int getSix13() {
        return six13;
    }


    public void setSix13(int six13) {
        this.six13 = six13;
    }


    public int getSix14() {
        return six14;
    }


    public void setSix14(int six14) {
        this.six14 = six14;
    }


    public int getSix15() {
        return six15;
    }


    public void setSix15(int six15) {
        this.six15 = six15;
    }


    public int getSix16() {
        return six16;
    }


    public void setSix16(int six16) {
        this.six16 = six16;
    }


    public int getSix17() {
        return six17;
    }


    public void setSix17(int six17) {
        this.six17 = six17;
    }


    public int getSix18() {
        return six18;
    }


    public void setSix18(int six18) {
        this.six18 = six18;
    }


    public int getSix19() {
        return six19;
    }


    public void setSix19(int six19) {
        this.six19 = six19;
    }


    public int getSix20() {
        return six20;
    }


    public void setSix20(int six20) {
        this.six20 = six20;
    }


    public int getSix21() {
        return six21;
    }


    public void setSix21(int six21) {
        this.six21 = six21;
    }


    public int getSix22() {
        return six22;
    }


    public void setSix22(int six22) {
        this.six22 = six22;
    }


    public int getSix23() {
        return six23;
    }


    public void setSix23(int six23) {
        this.six23 = six23;
    }


    public int getSix24() {
        return six24;
    }


    public void setSix24(int six24) {
        this.six24 = six24;
    }


    public int getSix25() {
        return six25;
    }


    public void setSix25(int six25) {
        this.six25 = six25;
    }


    public int getSix26() {
        return six26;
    }


    public void setSix26(int six26) {
        this.six26 = six26;
    }


    public int getSix27() {
        return six27;
    }


    public void setSix27(int six27) {
        this.six27 = six27;
    }


    public int getSix28() {
        return six28;
    }


    public void setSix28(int six28) {
        this.six28 = six28;
    }


    public int getSix29() {
        return six29;
    }


    public void setSix29(int six29) {
        this.six29 = six29;
    }


    public int getSix30() {
        return six30;
    }


    public void setSix30(int six30) {
        this.six30 = six30;
    }


    public int getSix31() {
        return six31;
    }


    public void setSix31(int six31) {
        this.six31 = six31;
    }


    public int getSix32() {
        return six32;
    }


    public void setSix32(int six32) {
        this.six32 = six32;
    }


    public int getSix33() {
        return six33;
    }


    public void setSix33(int six33) {
        this.six33 = six33;
    }


    public int getOne1() {
        return one1;
    }


    public void setOne1(int one1) {
        this.one1 = one1;
    }


    public int getOne2() {
        return one2;
    }


    public void setOne2(int one2) {
        this.one2 = one2;
    }


    public int getOne3() {
        return one3;
    }


    public void setOne3(int one3) {
        this.one3 = one3;
    }


    public int getOne4() {
        return one4;
    }


    public void setOne4(int one4) {
        this.one4 = one4;
    }


    public int getOne5() {
        return one5;
    }


    public void setOne5(int one5) {
        this.one5 = one5;
    }


    public int getOne6() {
        return one6;
    }


    public void setOne6(int one6) {
        this.one6 = one6;
    }


    public int getOne7() {
        return one7;
    }


    public void setOne7(int one7) {
        this.one7 = one7;
    }


    public int getOne8() {
        return one8;
    }


    public void setOne8(int one8) {
        this.one8 = one8;
    }


    public int getOne9() {
        return one9;
    }


    public void setOne9(int one9) {
        this.one9 = one9;
    }


    public int getOne10() {
        return one10;
    }


    public void setOne10(int one10) {
        this.one10 = one10;
    }


    public int getOne11() {
        return one11;
    }


    public void setOne11(int one11) {
        this.one11 = one11;
    }


    public int getOne12() {
        return one12;
    }


    public void setOne12(int one12) {
        this.one12 = one12;
    }


    public int getOne13() {
        return one13;
    }


    public void setOne13(int one13) {
        this.one13 = one13;
    }


    public int getOne14() {
        return one14;
    }


    public void setOne14(int one14) {
        this.one14 = one14;
    }


    public int getOne15() {
        return one15;
    }


    public void setOne15(int one15) {
        this.one15 = one15;
    }


    public int getOne16() {
        return one16;
    }


    public void setOne16(int one16) {
        this.one16 = one16;
    }


    public Date getCreateTime() {
        return createTime;
    }


    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

}
