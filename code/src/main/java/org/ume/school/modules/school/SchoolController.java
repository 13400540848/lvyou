package org.ume.school.modules.school;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Zz on 2021/3/21.
 */
@RestController
@RequestMapping("/v0.1/school")
public class SchoolController {

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
