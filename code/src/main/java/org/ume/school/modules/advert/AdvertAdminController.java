package org.ume.school.modules.advert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ume.school.modules.model.ErrorTag;
import org.ume.school.modules.model.entity.Advert;
import org.ume.school.modules.model.enums.AdvertLinkType;
import org.ume.school.modules.model.enums.AdvertLocation;
import org.ume.school.modules.model.enums.AdvertStatus;
import org.ume.school.modules.response.EnumResponse;

import com.bluesimon.wbf.AdminLogined;
import com.bluesimon.wbf.IUser;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.utils.AuthValidate;

/**
 * Created by Zz on 2018/8/30.
 */
@RestController
@RequestMapping("/v0.1/admin/advert")
public class AdvertAdminController {

    @Resource
    private AdvertService advertService;

//    /**
//     * 获取详情
//     */
//    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//    public Response<Advert> getAdvertById(@PathVariable("id") String id, @AdminLogined IUser user) {
//        Response<Advert> check = AuthValidate.checkAdmin(user);
//        if (check != null) {
//            return check;
//        }
//        Advert model = advertService.findById(id);
//        return new Response<>(model);
//    }
//    
//    /**
//     * 获取广告位置列表
//     */
//    @RequestMapping(value = "/location", method = RequestMethod.GET)
//    public Response<List<EnumResponse>> getAdvertLocations(@AdminLogined IUser user) {
//        Response<List<EnumResponse>> check = AuthValidate.checkAdmin(user);
//        if (check != null) {
//            return check;
//        }
//        List<EnumResponse> list = new ArrayList<EnumResponse>();
//        for(AdvertLocation p : AdvertLocation.values()){
//            EnumResponse er = new EnumResponse();
//            er.setText(p.getText());
//            er.setValue(p.getValue());
//            list.add(er);
//        }
//        return new Response<>(list);
//    }
//    
//    /**
//     * 获取广告状态列表
//     */
//    @RequestMapping(value = "/status", method = RequestMethod.GET)
//    public Response<List<EnumResponse>> getAdvertStatus(@AdminLogined IUser user) {
//        Response<List<EnumResponse>> check = AuthValidate.checkAdmin(user);
//        if (check != null) {
//            return check;
//        }
//        List<EnumResponse> list = new ArrayList<EnumResponse>();
//        for(AdvertStatus p : AdvertStatus.values()){
//            EnumResponse er = new EnumResponse();
//            er.setText(p.getText());
//            er.setValue(p.getValue());
//            list.add(er);
//        }
//        return new Response<>(list);
//    }
//    /**
//     * 获取广告链接方式列表
//     */
//    @RequestMapping(value = "/link_type", method = RequestMethod.GET)
//    public Response<List<EnumResponse>> getAdvertLinkType(@AdminLogined IUser user) {
//        Response<List<EnumResponse>> check = AuthValidate.checkAdmin(user);
//        if (check != null) {
//            return check;
//        }
//        List<EnumResponse> list = new ArrayList<EnumResponse>();
//        for(AdvertLinkType p : AdvertLinkType.values()){
//            EnumResponse er = new EnumResponse();
//            er.setText(p.getText());
//            er.setValue(p.getValue());
//            list.add(er);
//        }
//        return new Response<>(list);
//    }
//    
//
//    /**
//     * 全部数据
//     */
//    @RequestMapping(value = "", method = RequestMethod.GET)
//    public Response<List<Advert>> getAdminAdverts(@RequestParam(value = "title", required = false) String title,
//            @RequestParam(value = "status", required = false) Integer status,
//            @RequestParam(value = "location", required = false) Integer location,
//            @RequestParam(value = "offset", required = true) String offset,
//            @RequestParam(value = "limit", required = true) String limit, @AdminLogined IUser user) {
//        Response<List<Advert>> check = AuthValidate.checkAdmin(user);
//        if (check != null) {
//            return check;
//        }
//        Response<List<Advert>> result = new Response<List<Advert>>();
//        final Advert query = new Advert();
//        query.setTitle(title);
//        query.setStatus(status);
//        query.setLocation(location);
//        Page<Advert> projects = advertService.findAll(query, offset, limit);
//        result.setTotal(projects.getTotalElements());
//        result.setRows(projects.getContent());
//        return result;
//    }
//
//    /**
//    * 提交
//    * @return
//    */
//    @RequestMapping(value = "", method = RequestMethod.POST)
//    public Response<Advert> submitAdvert(@RequestBody Advert model, @AdminLogined IUser user) {
//        Response<Advert> check = AuthValidate.checkAdmin(user);
//        if (check != null) {
//            return check;
//        }
//        Response<Advert> resp = new Response<>();
//        if (StringUtils.isEmpty(model.getId())) { //新增
//            model.setId(UUID.randomUUID().toString());
//            model.setCreateTime(new Date());
////            model.setAdminId(user.getId());
//            model.setAdminName(user.getNickName());
//            resp.setRows(advertService.save(model));
//        }
//        else { // 修改
//            Advert modelDb = advertService.findById(model.getId());
//            if(modelDb==null){
//                resp.setResultCode(ErrorTag.ADVERT_NOT_EXISTS.getCode());
//                resp.setResultMsg(ErrorTag.ADVERT_NOT_EXISTS.getMessage());
//            }
//            else{
//                model.setUpdateTime(new Date());
////                model.setAdminId(user.getId());
//                model.setAdminName(user.getNickName());
//                resp.setRows(advertService.save(model));
//            }
//        }
//        return resp;
//    }
//    
//    /**
//     * 删除
//     */
//     @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
//     public Response<Advert> deleteAdvert(@PathVariable("id") String id, @AdminLogined IUser user) {
//         Response<Advert> check = AuthValidate.checkAdmin(user);
//         if (check != null) {
//             return check;
//         }
//         Response<Advert> resp = new Response<>();
//         if (!StringUtils.isEmpty(id)) {
//             Advert modelDb = advertService.findById(id);
//             if(modelDb==null){
//                 resp.setResultCode(ErrorTag.ADVERT_NOT_EXISTS.getCode());
//                 resp.setResultMsg(ErrorTag.ADVERT_NOT_EXISTS.getMessage());
//             }
//             else{
//                 advertService.delete(modelDb);
//             }
//         }
//         else { 
//             resp.setResultCode(ErrorTag.PARAM_NULL.getCode());
//             resp.setResultMsg(ErrorTag.PARAM_NULL.getMessage());
//         }
//         return resp;
//     }
}
