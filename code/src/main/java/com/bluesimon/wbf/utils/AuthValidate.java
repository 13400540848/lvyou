package com.bluesimon.wbf.utils;

import com.bluesimon.wbf.IUser;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.modules.user.enums.UserTypeEnum;

public class AuthValidate {
    public static <T> Response<T> checkAdmin(IUser user){
        Response<T> resp = null;
        if (user == null) {
            resp = new Response<T>("-1000", "登录已过期，请先登录");
        } 
        else if(user.getType()==null || (user.getType().intValue() != UserTypeEnum.ADMIN.getValue() && user.getType().intValue() != UserTypeEnum.SYSTEM.getValue())){
            resp = new Response<T>("不是后台用户");
        }
        return resp;
    }
    
    public static <T> Response<T> checkUser(IUser user){
        Response<T> resp = null;
        if (user == null) {
            resp = new Response<T>("-1000", "登录已过期，请先登录");
        } 
        else if(user.getType()==null || user.getType().intValue() == UserTypeEnum.ADMIN.getValue()){
            resp = new Response<T>("账号是后台用户");
        }
        return resp;
    }
}
