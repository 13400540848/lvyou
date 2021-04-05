package com.bluesimon.wbf.modules.role.menu;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 设置角色菜单
 */
@Getter
@Setter
public class RoleMenuSetDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private Long roleId;

    private Long menuId;
    
    private List<Long> addIds;
    
    private List<Long> deleteIds;
}
