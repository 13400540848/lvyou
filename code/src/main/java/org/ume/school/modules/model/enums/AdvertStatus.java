package org.ume.school.modules.model.enums;

/*
 * 状态，0 待发布  1 已发布  2 下架
 */
public enum AdvertStatus {
    UNPUBLISH("待发布", 0),
    PUBLISHED("已发布",1),
    UNLINE("下架",2);
    
    private String text;
    private int value;
    
    // 构造方法
    private AdvertStatus(String text, int value) {
        this.text = text;
        this.value = value;
    }   
    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
}
