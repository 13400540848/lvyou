package org.ume.school.modules.config;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.ume.school.modules.model.ErrorTag;
import org.ume.school.modules.model.entity.Config;

import com.bluesimon.wbf.AdminLogined;
import com.bluesimon.wbf.IUser;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.utils.AuthValidate;

/**
 * Created by Zz on 2018/10/13.
 */
@RestController
@RequestMapping("/v0.1/admin/config")
public class ConfigAdminController {

    @Resource
    private ConfigService configService;

//    /**
//     * 获取详情
//     */
//    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//    public Response<Config> getConfigById(@PathVariable("id") String id) {
//        Config model = configService.findById(id);
//        return new Response<>(model);
//    }
//    
//    /**
//     * 获取列表
//     */
//    @RequestMapping(value = "", method = RequestMethod.GET)
//    public Response<List<Config>> getConfigs(@AdminLogined IUser user) {
//        Response<List<Config>> check = AuthValidate.checkAdmin(user);
//        if (check != null) {
//            return check;
//        }
//        List<Config> list = configService.findList();
//        return new Response<>(list);
//    }
//
//    /**
//    * 提交
//    * @return
//    */
//    @RequestMapping(value = "", method = RequestMethod.POST)
//    public Response<Config> submitConfig(@RequestBody Config model, @AdminLogined IUser user) {
//        Response<Config> check = AuthValidate.checkAdmin(user);
//        if (check != null) {
//            return check;
//        }
//        Response<Config> resp = new Response<>();
//        if (StringUtils.isEmpty(model.getId())) { //新增
//            model.setId(UUID.randomUUID().toString());
//            resp.setRows(configService.save(model));
//        }
//        else { // 修改
//            Config modelDb = configService.findById(model.getId());
//            if(modelDb==null){
//                resp.setResultCode(ErrorTag.CONFIG_NOT_EXISTS.getCode());
//                resp.setResultMsg(ErrorTag.CONFIG_NOT_EXISTS.getMessage());
//            }
//            else{
//                resp.setRows(configService.save(model));
//            }
//        }
//        return resp;
//    }
//    
//    /**
//     * 删除
//     * @return
//     */
//     @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
//     public Response<Config> deleteConfig(@PathVariable("id") String id, @AdminLogined IUser user) {
//         Response<Config> check = AuthValidate.checkAdmin(user);
//         if (check != null) {
//             return check;
//         }
//         Response<Config> resp = new Response<>();
//         if (!StringUtils.isEmpty(id)) {
//             Config modelDb = configService.findById(id);
//             if(modelDb==null){
//                 resp.setResultCode(ErrorTag.CONFIG_NOT_EXISTS.getCode());
//                 resp.setResultMsg(ErrorTag.CONFIG_NOT_EXISTS.getMessage());
//             }
//             else{
//                 configService.delete(modelDb);
//             }
//         }
//         else { 
//             resp.setResultCode(ErrorTag.PARAM_NULL.getCode());
//             resp.setResultMsg(ErrorTag.PARAM_NULL.getMessage());
//         }
//         return resp;
//     }
}
