package com.bluesimon.wbf;

import java.util.List;

/**
 * Created by Django on 2017/7/16.
 */
public interface IUser {

    String LOGIN_USER = "loginUser";
    
    String LOGIN_ADMIN_USER = "loginAdminUser";
    
    String SMS_CODE = "smsCode";

    Long getId();

    String getAccount();
    
    String getNickName();
    
    String getRealName();

    List<String> getRole();

    Integer getType();
}
