package com.bluesimon.wbf.authentication;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.bluesimon.wbf.IUser;

/**
 * Created by Django on 2017/12/15.
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    public static ArrayList<String> backListPages = new ArrayList<>();

    static {
        backListPages.add("usercenter");
        backListPages.add("editor");
    }

    @Override
    public boolean checkApi() {
        return true;
    }

    /**
     * 1、黑名单中默认不让访问
     * 2、但如果登录了也可以访问
     *
     * @param module
     * @return
     */
    @Override
    public boolean checkBackList(String module, IUser user) {
        if (backListPages.contains(module)) {
            if (null != user) {
                return true;
            }
            return false;
        }
        return true;
    }
}
