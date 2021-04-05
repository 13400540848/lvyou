package org.ume.school.modules.model.enums;

/*
 * 广告位  0首页轮播  1首页通知 2关于我们
 */
public enum AdvertLocation {
    INDEX_PLAY("首页轮播", 0),
    INDEX_NOTICE("首页通知", 1),
    INDEX_OFFICIAL_NOTICE("官方公告", 2),
    INDEX_LATEST_ACTIVE("最新活动", 3),
    INDEX_LINK("友情链接", 4),
    ABOUT_US("关于我们", 10);
    
    private String text;
    private int value;
    
    // 构造方法
    private AdvertLocation(String text, int value) {
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
