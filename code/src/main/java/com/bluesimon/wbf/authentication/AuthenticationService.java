package com.bluesimon.wbf.authentication;

import com.bluesimon.wbf.IUser;

/**
 * Created by Django on 2017/12/5.
 */
public interface AuthenticationService {

    boolean checkApi();

    /**
     * 检测黑名单内的页面访问时是否登录？
     *
     * @param page
     * @return
     */
    boolean checkBackList(String page, IUser user);
}