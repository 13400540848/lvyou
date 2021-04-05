package org.ume.school.modules.request;

import java.io.Serializable;
import java.util.List;

import org.ume.school.modules.model.entity.UserPlaySeven;

/**
 * 用户投注
 * Created by Zz on 2018/9/1.
 */
public class UserPlaySevenRequest implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private List<UserPlaySeven> data;
    
    private String validateCode;
    
    private String dealPassword; 
    
    public UserPlaySevenRequest() {
        
    }

	public List<UserPlaySeven> getData() {
		return data;
	}

	public void setData(List<UserPlaySeven> data) {
		this.data = data;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	public String getDealPassword() {
		return dealPassword;
	}

	public void setDealPassword(String dealPassword) {
		this.dealPassword = dealPassword;
	}
    
}
