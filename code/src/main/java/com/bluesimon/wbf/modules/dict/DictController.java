package com.bluesimon.wbf.modules.dict;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bluesimon.wbf.IUser;
import com.bluesimon.wbf.Logined;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.utils.AuthValidate;

/**
 * Created by Zz on 2021/3/20.
 */
@RestController
@RequestMapping("/v0.1/dict")
public class DictController {

    @Resource
    private DictService dictService;
    
//    /**
//     * 获取字典列表(多个Key)
//     */
//    @RequestMapping(value = "/list", method = RequestMethod.GET)
//    public Response<List<DictEntity>> getNav(@RequestBody(required = true) List<String> keys, @Logined IUser user) {
//        Response<List<DictEntity>> check = AuthValidate.checkUser(user);
//        if(check!=null){
//            return check;
//        }
//        if(keys.isEmpty()){
//            return new Response<>();
//        }
////        User u = userService.getUser(user.getId());
////        u.setPassword("");
//        return new Response<>();
//    }
    
//    /**
//     * 获取当前用户的门户的菜单列表
//     */
//    @RequestMapping(value = "/nav/{id}/menu", method = RequestMethod.GET)
//    public Response<DictEntity> getMenu(@PathVariable("id") Long id, @Logined IUser user) {
//        Response<DictEntity> check = AuthValidate.checkUser(user);
//        if(check!=null){
//            return check;
//        }
////        User u = userService.getUser(user.getId());
////        u.setPassword("");
//        return new Response<>();
//    }
}
