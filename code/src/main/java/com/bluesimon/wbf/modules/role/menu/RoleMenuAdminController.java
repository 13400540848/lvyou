package com.bluesimon.wbf.modules.role.menu;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bluesimon.wbf.AdminLogined;
import com.bluesimon.wbf.IUser;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.ResponseErrorEnum;
import com.bluesimon.wbf.modules.menu.MenuEntity;
import com.bluesimon.wbf.modules.menu.MenuService;
import com.bluesimon.wbf.modules.menu.MenuTypeEnum;
import com.bluesimon.wbf.utils.AuthValidate;
import com.bluesimon.wbf.utils.StringUtil;

/**
 * Created by Zz on 2021/3/20.
 */
@RestController
@RequestMapping("/v0.1/admin/role/menu")
public class RoleMenuAdminController {

    @Resource
    private RoleMenuService roleMenuService;

    @Resource
    private MenuService menuService;

    /**
     * 角色获取菜单列表
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<List<RoleMenuEntity>> getByRoleId(@PathVariable("id") Long id, @AdminLogined IUser user) {
        Response<List<RoleMenuEntity>> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        List<RoleMenuEntity> rows = roleMenuService.getByRoleId(id);
        return new Response<>(rows);
    }

    /**
     * 角色获取所有菜单列表（菜单设置）
     */
    @RequestMapping(value = "/set/{id}", method = RequestMethod.GET)
    public Response<List<MenuEntity>> getAll(@PathVariable("id") Long id, @AdminLogined IUser user) {
        Response<List<MenuEntity>> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        List<MenuEntity> navs = menuService.getByType(MenuTypeEnum.NAV.getValue());
        List<MenuEntity> menus = menuService.getByType(MenuTypeEnum.MENU.getValue());
        List<RoleMenuEntity> rows = roleMenuService.getByRoleId(id);
        if (!StringUtil.isEmpty(navs) && !StringUtil.isEmpty(menus)) {
            for (MenuEntity nav : navs) {
                List<MenuEntity> children = new ArrayList<>();
                for (MenuEntity menu : menus) {
                    if (menu.getPId().equals(nav.getId())) {
                        menu.setIcon(null);
                        menu.setChecked(false);
                        if (!StringUtil.isEmpty(rows)) {
                            for (RoleMenuEntity row : rows) {
                                if (row.getMenuId().equals(menu.getId())) {
                                    menu.setChecked(true);
                                    break;
                                }
                            }
                        }
                        children.add(menu);
                    }
                }
                nav.setChildren(children);
            }
        }
        return new Response<>(navs);
    }

    /**
     * 设置角色菜单
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Response<RoleMenuEntity> submit(@RequestBody(required = true) RoleMenuSetDTO entity, @AdminLogined IUser user) {
        Response<RoleMenuEntity> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        if (StringUtil.isEmpty(entity.getRoleId()) || (StringUtil.isEmpty(entity.getAddIds()) && StringUtil.isEmpty(entity.getDeleteIds()))) {
            return new Response<>(ResponseErrorEnum.PARAM_NULL);
        }
        roleMenuService.saveSetByRole(entity.getRoleId(), entity.getAddIds(), entity.getDeleteIds());
        return new Response<>();
    }

//    /**
//     * 详情
//     */
//    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//    public Response<RoleMenuEntity> get(@PathVariable("id") Long id, @AdminLogined IUser user) {
//        Response<RoleMenuEntity> check = AuthValidate.checkAdmin(user);
//        if (check != null) {
//            return check;
//        } 
//        return roleMenuService.get(id);
//    }
//    
//    /**
//     * 保存
//     */
//    @RequestMapping(value = "", method = RequestMethod.POST)
//    public Response<RoleMenuEntity> submit(@RequestBody(required = true) RoleMenuEntity entity, @AdminLogined IUser user) {
//        Response<RoleMenuEntity> check = AuthValidate.checkAdmin(user);
//        if (check != null) {
//            return check;
//        }
//        if(StringUtil.isEmpty(entity.getName()) || StringUtil.isEmpty(entity.getCode())){
//            return new Response<>(ResponseErrorEnum.PARAM_NULL);
//        }
//        if (StringUtil.isEmpty(entity.getId())) { //新增
//            return roleService.add(entity);
//        }else{
//            return roleService.edit(entity);
//        }
//    }
//    
//    /**
//     * 删除
//     */
//    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
//    public Response<RoleMenuEntity> delete(@PathVariable("id") Long id, @AdminLogined IUser user) {
//        Response<RoleMenuEntity> check = AuthValidate.checkAdmin(user);
//        if (check != null) {
//            return check;
//        } 
//        return roleService.delete(id);
//    }
}
