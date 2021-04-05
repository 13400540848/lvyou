package org.ume.school.modules.model.enums;

/*
 * 0 待发布  1 已发布（未开始\进行中\时间结束\已筹满）  2 下架
 */
public enum ProjectStatus {
    UNPUBLISH(0),
    PUBLISHED(1),
    UNLINE(2);
    
    private int value;
    
 // 构造方法
    private ProjectStatus(int value) {
        this.value = value;
    }   
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
