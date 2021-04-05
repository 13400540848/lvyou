package com.bluesimon.wbf;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Django on 2017/7/18.
 */
public class Principle extends HashMap<String, String> {

    private IUser user;

    public Principle() {
    }

    public Principle(IUser user, Map<String, String> model) {
        this.user = user;
        if (null != model) {
            Iterator<String> iterator = model.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                String val = model.get(key);
                this.put(key, val);
            }
        }
    }

    public IUser getUser() {
        return user;
    }

    public void setUser(IUser user) {
        this.user = user;
    }
}
