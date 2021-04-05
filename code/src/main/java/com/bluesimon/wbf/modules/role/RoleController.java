package com.bluesimon.wbf.modules.role;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bluesimon.wbf.IUser;
import com.bluesimon.wbf.Logined;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.utils.AuthValidate;

/**
 * Created by Zz on 2021/3/21.
 */
@RestController
@RequestMapping("/v0.1/role")
public class RoleController {

//    @Resource
//    private RoleService roleService;
    
//    /**
//     * 获取当前用户的门户列表
//     */
//    @RequestMapping(value = "/nav", method = RequestMethod.GET)
//    public Response<AttachmentEntity> getNav(@Logined IUser user) {
//        Response<AttachmentEntity> check = AuthValidate.checkUser(user);
//        if(check!=null){
//            return check;
//        }
////        User u = userService.getUser(user.getId());
////        u.setPassword("");
//        return new Response<>();
//    }
//    
//    /**
//     * 获取当前用户的门户的菜单列表
//     */
//    @RequestMapping(value = "/nav/{id}/menu", method = RequestMethod.GET)
//    public Response<AttachmentEntity> getMenu(@PathVariable("id") Long id, @Logined IUser user) {
//        Response<AttachmentEntity> check = AuthValidate.checkUser(user);
//        if(check!=null){
//            return check;
//        }
////        User u = userService.getUser(user.getId());
////        u.setPassword("");
//        return new Response<>();
//    }
}
