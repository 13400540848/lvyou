package com.bluesimon.wbf.modules.dict.item;

import java.util.ArrayList;
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
import com.bluesimon.wbf.modules.dict.DictEntity;
import com.bluesimon.wbf.modules.dict.DictService;
import com.bluesimon.wbf.utils.AuthValidate;

/**
 * Created by Zz on 2021/3/21.
 */
@RestController
@RequestMapping("/v0.1/dict/item")
public class DictItemController {

    @Resource
    private DictService dictService;
    
    @Resource
    private DictItemService dictItemService;
    
    /**
     * 获取字典项列表(多个Key)
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Response<List<DictItemEntity>> getItemsByCodes(@RequestBody(required = true) List<String> codes, @Logined IUser user) {
        Response<List<DictItemEntity>> check = AuthValidate.checkUser(user);
        if(check!=null){
            return check;
        }
        if(codes == null || codes.isEmpty()){
            return new Response<>();
        }
        List<DictEntity> dicts = dictService.getByCodes(codes);
        if(dicts!=null &&!dicts.isEmpty()){
            final List<Long> dictIds = new ArrayList<>();
            for(DictEntity d : dicts){
                dictIds.add(d.getId());
            }
            List<DictItemEntity> rows = dictItemService.getByDictIds(dictIds);
            return new Response<>(rows);
        }
        return new Response<>();
    }
    
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
