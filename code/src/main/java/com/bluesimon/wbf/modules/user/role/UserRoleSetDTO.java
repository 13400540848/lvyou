package com.bluesimon.wbf.modules.user.role;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 设置用户角色
 */
@Getter
@Setter
public class UserRoleSetDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private Long userId;
    
    private List<Long> addIds;
    
    private List<Long> deleteIds;
}
