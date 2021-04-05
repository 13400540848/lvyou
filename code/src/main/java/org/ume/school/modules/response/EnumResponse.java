package org.ume.school.modules.response;

import java.io.Serializable;

/**
 * 枚举返回列表
 * Created by Zz on 2018/11/27.
 */
public class EnumResponse implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String text;
    
    private int value; 
    
    public EnumResponse() {
        
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
