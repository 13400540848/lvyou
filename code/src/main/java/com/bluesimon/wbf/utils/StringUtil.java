package com.bluesimon.wbf.utils;

import java.util.List;


public class StringUtil {
      
    public static boolean isEmpty(String str) {  
        return str==null || str.length()==0;
    } 
    public static boolean isEmpty(Integer i) {  
        return i==null;
    } 
    public static boolean isEmpty(Long l) {  
        return l==null;
    } 
    public static boolean isEmpty(List list) {  
        return list==null || list.isEmpty();
    }
    
}  