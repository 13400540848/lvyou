package com.bluesimon.wbf.modules.role;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bluesimon.wbf.AdminLogined;
import com.bluesimon.wbf.IUser;
import com.bluesimon.wbf.RequestPager;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.ResponseErrorEnum;
import com.bluesimon.wbf.modules.menu.MenuEntity;
import com.bluesimon.wbf.utils.AuthValidate;
import com.bluesimon.wbf.utils.StringUtil;

/**
 * Created by Zz on 2021/3/20.
 */
@RestController
@RequestMapping("/v0.1/admin/role")
public class RoleAdminController {

    @Resource
    private RoleService roleService;
    
    /**
     * 查询
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Response<List<RoleEntity>> getAdminMenus(@RequestBody RequestPager<RoleEntity> req, @AdminLogined IUser user) {
        Response<List<RoleEntity>> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        Response<List<RoleEntity>> result = new Response<List<RoleEntity>>();
        Page<RoleEntity> projects = roleService.findAll(req);
        result.setTotal(projects.getTotalElements());
        result.setRows(projects.getContent());
        return result;
    }
    
    /**
     * 详情
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<RoleEntity> get(@PathVariable("id") Long id, @AdminLogined IUser user) {
        Response<RoleEntity> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        } 
        return roleService.get(id);
    }
    
    /**
     * 保存
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Response<RoleEntity> submit(@RequestBody(required = true) RoleEntity entity, @AdminLogined IUser user) {
        Response<RoleEntity> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        if(StringUtil.isEmpty(entity.getName()) || StringUtil.isEmpty(entity.getCode())){
            return new Response<>(ResponseErrorEnum.PARAM_NULL);
        }
        if (StringUtil.isEmpty(entity.getId())) { //新增
            return roleService.add(entity);
        }else{
            return roleService.edit(entity);
        }
    }
    
    /**
     * 删除
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Response<RoleEntity> delete(@PathVariable("id") Long id, @AdminLogined IUser user) {
        Response<RoleEntity> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        } 
        return roleService.delete(id);
    }
}
