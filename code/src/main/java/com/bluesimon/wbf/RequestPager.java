package com.bluesimon.wbf;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by Zz on 2021/3/21.
 */
@Getter
@Setter
@Data
public class RequestPager<T> implements Serializable {

    private Integer pageSize;
    
    private Integer pageIndex;
    
    private T condition;
    

    public RequestPager() {
        
    }
    
    public RequestPager(T condition) {
        this.condition = condition;
    }

}
