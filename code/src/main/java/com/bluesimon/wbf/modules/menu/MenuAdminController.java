package com.bluesimon.wbf.modules.menu;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bluesimon.wbf.AdminLogined;
import com.bluesimon.wbf.IUser;
import com.bluesimon.wbf.Logined;
import com.bluesimon.wbf.RequestPager;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.ResponseErrorEnum;
import com.bluesimon.wbf.utils.AuthValidate;
import com.bluesimon.wbf.utils.StringUtil;

/**
 * Created by Zz on 2021/3/20.
 */
@RestController
@RequestMapping("/v0.1/admin/menu")
public class MenuAdminController {

    @Resource
    private MenuService menuService;
    
    /**
     * 查询
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Response<List<MenuEntity>> page(@RequestBody RequestPager<MenuEntity> req, @AdminLogined IUser user) {
        Response<List<MenuEntity>> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        Response<List<MenuEntity>> result = new Response<List<MenuEntity>>();
        Page<MenuEntity> projects = menuService.findAll(req);
        result.setTotal(projects.getTotalElements());
        result.setRows(projects.getContent());
        return result;
    }
    
//    /**
//     * 获取门户和菜单列表
//     */
//    @RequestMapping(value = "/all", method = RequestMethod.GET)
//    public Response<List<MenuEntity>> getAll(@AdminLogined IUser user) {
//        Response<List<MenuEntity>> check = AuthValidate.checkAdmin(user);
//        if(check!=null){
//            return check;
//        }        
//        List<MenuEntity> navs=menuService.getByType(MenuTypeEnum.NAV.getValue());
//        List<MenuEntity> menus=menuService.getByType(MenuTypeEnum.MENU.getValue());
//        if(!StringUtil.isEmpty(navs) && !StringUtil.isEmpty(menus)){
//            for(MenuEntity nav : navs){
//                List<MenuEntity> children = new ArrayList<>();
//                for(MenuEntity menu : menus){
//                    if(menu.getPId().equals(nav.getId())){
//                        children.add(menu);
//                    }
//                }
//                nav.setChildren(children);
//            }
//        }
//        return new Response<>(navs);
//    }
    
    /**
     * 获取门户列表
     */
    @RequestMapping(value = "/navs", method = RequestMethod.GET)
    public Response<List<MenuEntity>> getNavs(@AdminLogined IUser user) {
        Response<List<MenuEntity>> check = AuthValidate.checkAdmin(user);
        if(check!=null){
            return check;
        }
        List<MenuEntity> list=menuService.getByType(MenuTypeEnum.NAV.getValue());
        return new Response<>(list);
    }

    
    /**
     * 根据门户获取菜单列表
     */
    @RequestMapping(value = "/{pid}/menus", method = RequestMethod.GET)
    public Response<List<MenuEntity>> getMenu(@PathVariable("pid") Long pId, @Logined IUser user) {
        Response<List<MenuEntity>> check = AuthValidate.checkUser(user);
        if(check!=null){
            return check;
        }
        List<MenuEntity> list=menuService.getByPId(pId);
        return new Response<>(list);
    }
    
    /**
     * 详情
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<MenuEntity> get(@PathVariable("id") Long id, @AdminLogined IUser user) {
        Response<MenuEntity> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        } 
        return menuService.get(id);
    }
    
    /**
     * 保存
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Response<MenuEntity> submit(@RequestBody(required = true) MenuEntity entity, @AdminLogined IUser user) {
        Response<MenuEntity> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        if(StringUtil.isEmpty(entity.getName()) || StringUtil.isEmpty(entity.getCode())){
            return new Response<>(ResponseErrorEnum.PARAM_NULL);
        }
        if (StringUtil.isEmpty(entity.getId())) { //新增
            return menuService.add(entity);
        }else{
            return menuService.edit(entity);
        }
    }
    
    /**
     * 删除
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Response<MenuEntity> delete(@PathVariable("id") Long id, @AdminLogined IUser user) {
        Response<MenuEntity> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        } 
        return menuService.delete(id);
    }
}
