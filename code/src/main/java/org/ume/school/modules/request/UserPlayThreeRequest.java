package org.ume.school.modules.request;

import java.io.Serializable;
import java.util.List;

import org.ume.school.modules.model.entity.UserPlayThree;

/**
 * 用户投注
 * Created by Zz on 2018/11/1.
 */
public class UserPlayThreeRequest implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private List<UserPlayThree> data;
    
    private String validateCode;
    
    private String dealPassword; 
    
    public UserPlayThreeRequest() {
        
    }

	public List<UserPlayThree> getData() {
		return data;
	}

	public void setData(List<UserPlayThree> data) {
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
