package org.ume.school.modules.model.enums;

public enum ProjectRecommend {
    YES(1),
    NO(0);
    
    private int value;
    
 // 构造方法
    private ProjectRecommend(int value) {
        this.value = value;
    }   
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
