package org.ume.school.modules.model.enums;

/*
 * 链接方式  0内部广告  1项目链接 2外部链接
 */
public enum AdvertLinkType {
    ADVERT("内部广告", 0),
    PROJECT("项目链接", 1),
    OUTSITE("外部链接", 2);
    
    private String text;
    private int value;
    
    // 构造方法
    private AdvertLinkType(String text, int value) {
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
