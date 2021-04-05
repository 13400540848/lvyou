package com.bluesimon.wbf.modules.menu.role;

import com.bluesimon.wbf.AdminLogined;
import com.bluesimon.wbf.IUser;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.ResponseErrorEnum;
import com.bluesimon.wbf.modules.menu.MenuEntity;
import com.bluesimon.wbf.modules.menu.MenuService;
import com.bluesimon.wbf.modules.menu.MenuTypeEnum;
import com.bluesimon.wbf.modules.role.RoleEntity;
import com.bluesimon.wbf.modules.role.RoleService;
import com.bluesimon.wbf.modules.role.menu.RoleMenuEntity;
import com.bluesimon.wbf.modules.role.menu.RoleMenuService;
import com.bluesimon.wbf.modules.role.menu.RoleMenuSetDTO;
import com.bluesimon.wbf.utils.AuthValidate;
import com.bluesimon.wbf.utils.StringUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zz on 2021/3/20.
 */
@RestController
@RequestMapping("/v0.1/admin/menu/role")
public class MenuRoleAdminController {

    @Resource
    private RoleMenuService roleMenuService;
    
    @Resource
    private RoleService roleService;
    
    /**
     * 菜单获取角色列表
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<List<RoleMenuEntity>> getByMenuId(@PathVariable("id") Long id, @AdminLogined IUser user) {
        Response<List<RoleMenuEntity>> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        List<RoleMenuEntity> rows = roleMenuService.getByMenuId(id);
        return new Response<>(rows);
    }
    
    /**
     * 菜单获取所有角色列表（角色设置）
     */
    @RequestMapping(value = "/set/{id}", method = RequestMethod.GET)
    public Response<List<RoleEntity>> getAll(@PathVariable("id") Long id, @AdminLogined IUser user) {
        Response<List<RoleEntity>> check = AuthValidate.checkAdmin(user);
        if(check!=null){
            return check;
        }        
        List<RoleEntity> roles=roleService.getAll();
        List<RoleMenuEntity> rows = roleMenuService.getByMenuId(id);
        if(!StringUtil.isEmpty(roles) && !StringUtil.isEmpty(rows)){
            for(RoleEntity role : roles){
                for(RoleMenuEntity row : rows){
                    if(row.getRoleId().equals(role.getId())){
                        role.setChecked(true);
                        break;
                    }
                }
            }
        }
        return new Response<>(roles);
    }
    
      /**
      * 设置菜单角色
      */
     @RequestMapping(value = "/save", method = RequestMethod.POST)
     public Response<RoleMenuEntity> submit(@RequestBody(required = true) RoleMenuSetDTO entity, @AdminLogined IUser user) {
         Response<RoleMenuEntity> check = AuthValidate.checkAdmin(user);
         if (check != null) {
             return check;
         }
         if(StringUtil.isEmpty(entity.getMenuId()) || (StringUtil.isEmpty(entity.getAddIds()) && StringUtil.isEmpty(entity.getDeleteIds()))){
             return new Response<>(ResponseErrorEnum.PARAM_NULL);
         }
         roleMenuService.saveSetByMenu(entity.getMenuId(), entity.getAddIds(), entity.getDeleteIds());
         return new Response<RoleMenuEntity>();
     }
}
