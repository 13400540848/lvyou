package com.bluesimon.wbf.modules.user.dto;

import java.io.Serializable;

/**
 * 用户修改密码
 */
@SuppressWarnings("serial")
public class ModifyPasswordDTO implements Serializable {
    
    private String oldPassword;
    
    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
