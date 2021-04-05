package com.bluesimon.wbf.modules.user.role;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.bluesimon.wbf.modules.user.enums.UserTypeEnum;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bluesimon.wbf.AdminLogined;
import com.bluesimon.wbf.IUser;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.ResponseErrorEnum;
import com.bluesimon.wbf.modules.role.RoleEntity;
import com.bluesimon.wbf.modules.role.RoleService;
import com.bluesimon.wbf.modules.role.menu.RoleMenuEntity;
import com.bluesimon.wbf.modules.role.menu.RoleMenuSetDTO;
import com.bluesimon.wbf.utils.AuthValidate;
import com.bluesimon.wbf.utils.StringUtil;

/**
 * Created by Zz on 2021/3/20.
 */
@RestController
@RequestMapping("/v0.1/admin/user/role")
public class UserRoleAdminController {

    @Resource
    private UserRoleService userRoleService;
    
    @Resource
    private RoleService roleService;

    /**
     * 获取当前用户的角色
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Response<List<RoleEntity>> get(@AdminLogined IUser user) {
        Response<List<RoleEntity>> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        List<RoleEntity> roles = roleService.getAll();
        if(user.getType().equals(UserTypeEnum.ADMIN.getValue())){
            return new Response<>(roles);
        }
        List<RoleEntity> result = new ArrayList<>();
        List<UserRoleEntity> rows = userRoleService.getByUserId(user.getId());
        if(!StringUtil.isEmpty(roles) && !StringUtil.isEmpty(rows)){
            for(RoleEntity role : roles){
                role.setChecked(false);
                for(UserRoleEntity row : rows){
                    if(role.getId().equals(row.getRoleId())){
                        result.add(role);
                        break;
                    }
                }
            }
        }
        return new Response<>(result);
    }

    /**
     * 用户获取所有角色列表列表（用户角色设置）
     */
    @RequestMapping(value = "/set/{id}", method = RequestMethod.GET)
    public Response<List<RoleEntity>> getAll(@PathVariable("id") Long id, @AdminLogined IUser user) {
        Response<List<RoleEntity>> check = AuthValidate.checkAdmin(user);
        if(check!=null){
            return check;
        }        
        List<RoleEntity> roles=roleService.getAll();
        List<UserRoleEntity> rows = userRoleService.getByUserId(id);
        if(!StringUtil.isEmpty(roles) && !StringUtil.isEmpty(rows)){
            for(RoleEntity role : roles){
                role.setChecked(false);
                for(UserRoleEntity row : rows){
                    if(role.getId().equals(row.getRoleId())){
                        role.setChecked(true);
                        break;
                    }
                }
            }
        }
        return new Response<>(roles);
    }
    
    /**
     * 设置用户角色
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Response<UserRoleEntity> submit(@RequestBody(required = true) UserRoleSetDTO entity, @AdminLogined IUser user) {
        Response<UserRoleEntity> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        if(StringUtil.isEmpty(entity.getUserId()) || (StringUtil.isEmpty(entity.getAddIds()) && StringUtil.isEmpty(entity.getDeleteIds()))){
            return new Response<>(ResponseErrorEnum.PARAM_NULL);
        }
        userRoleService.saveSet(entity.getUserId(), entity.getAddIds(), entity.getDeleteIds());
        return new Response<>();
    }


//    
//    /**
//     * 查询
//     */
//    @RequestMapping(value = "", method = RequestMethod.GET)
//    public Response<List<UserRoleEntity>> getAdminMenus(@RequestBody RequestPager<UserRoleEntity> req, @AdminLogined IUser user) {
//        Response<List<UserRoleEntity>> check = AuthValidate.checkAdmin(user);
//        if (check != null) {
//            return check;
//        }
//        Response<List<UserRoleEntity>> result = new Response<List<UserRoleEntity>>();
//        Page<UserRoleEntity> projects = userRoleService.findAll(req);
//        result.setTotal(projects.getTotalElements());
//        result.setRows(projects.getContent());
//        return result;
//    }
//    
//    /**
//     * 详情
//     */
//    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//    public Response<UserRoleEntity> get(@PathVariable("id") Long id, @AdminLogined IUser user) {
//        Response<UserRoleEntity> check = AuthValidate.checkAdmin(user);
//        if (check != null) {
//            return check;
//        } 
//        return userRoleService.get(id);
//    }
//    
//    /**
//     * 保存
//     */
//    @RequestMapping(value = "", method = RequestMethod.POST)
//    public Response<UserRoleEntity> submit(@RequestBody(required = true) UserRoleEntity entity, @AdminLogined IUser user) {
//        Response<UserRoleEntity> check = AuthValidate.checkAdmin(user);
//        if (check != null) {
//            return check;
//        }
//        if(StringUtil.isEmpty(entity.getName()) || StringUtil.isEmpty(entity.getCode())){
//            return new Response<>(ResponseErrorEnum.PARAM_NULL);
//        }
//        if (StringUtil.isEmpty(entity.getId())) { //新增
//            return userRoleService.add(entity);
//        }else{
//            return userRoleService.edit(entity);
//        }
//    }
//    
//    /**
//     * 删除
//     */
//    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
//    public Response<UserRoleEntity> delete(@PathVariable("id") Long id, @AdminLogined IUser user) {
//        Response<UserRoleEntity> check = AuthValidate.checkAdmin(user);
//        if (check != null) {
//            return check;
//        } 
//        return userRoleService.delete(id);
//    }
}
