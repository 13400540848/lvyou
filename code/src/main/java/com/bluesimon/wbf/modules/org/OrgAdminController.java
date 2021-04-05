package com.bluesimon.wbf.modules.org;

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
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.ResponseErrorEnum;
import com.bluesimon.wbf.utils.AuthValidate;
import com.bluesimon.wbf.utils.StringUtil;

/**
 * Created by Zz on 2021/3/20.
 */
@RestController
@RequestMapping("/v0.1/admin/org")
public class OrgAdminController {

//    @Resource
//    private OrgService attachmentService;
    
//    /**
//     * 查询
//     */
//    @RequestMapping(value = "", method = RequestMethod.GET)
//    public Response<List<AttachmentEntity>> getAdminMenus(@RequestParam(value = "name", required = false) String name,
//            @RequestParam(value = "code", required = false) String code,
//            @RequestParam(value = "isHide", required = false) Integer isHide,
//            @RequestParam(value = "pId", required = false) Integer pId,
//            @RequestParam(value = "type", required = false) Integer type,
//            @RequestParam(value = "offset", required = true) String offset,
//            @RequestParam(value = "limit", required = true) String limit, @AdminLogined IUser user) {
//        Response<List<AttachmentEntity>> check = AuthValidate.checkAdmin(user);
//        if (check != null) {
//            return check;
//        }
//        Response<List<AttachmentEntity>> result = new Response<List<AttachmentEntity>>();
//        final AttachmentEntity query = new AttachmentEntity();
//        query.setName(name);
//        query.setCode(code);
//        query.setIsHide(isHide);
//        query.setPId(pId);
//        query.setType(type);
//        Page<AttachmentEntity> projects = attachmentService.findAll(query, offset, limit);
//        result.setTotal(projects.getTotalElements());
//        result.setRows(projects.getContent());
//        return result;
//    }
//    
//    /**
//     * 详情
//     */
//    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//    public Response<AttachmentEntity> get(@PathVariable("id") Long id, @AdminLogined IUser user) {
//        Response<AttachmentEntity> check = AuthValidate.checkAdmin(user);
//        if (check != null) {
//            return check;
//        } 
//        return attachmentService.delete(id);
//    }
//    
//    /**
//     * 保存
//     */
//    @RequestMapping(value = "", method = RequestMethod.POST)
//    public Response<AttachmentEntity> submit(@RequestBody(required = true) AttachmentEntity entity, @AdminLogined IUser user) {
//        Response<AttachmentEntity> check = AuthValidate.checkAdmin(user);
//        if (check != null) {
//            return check;
//        }
//        if(StringUtil.isEmpty(entity.getName()) || StringUtil.isEmpty(entity.getCode())){
//            return new Response<>(ResponseErrorEnum.PARAM_NULL);
//        }
//        if (StringUtil.isEmpty(entity.getId())) { //新增
//            return attachmentService.add(entity);
//        }else{
//            return attachmentService.edit(entity);
//        }
//    }
//    
//    /**
//     * 删除
//     */
//    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
//    public Response<AttachmentEntity> delete(@PathVariable("id") Long id, @AdminLogined IUser user) {
//        Response<AttachmentEntity> check = AuthValidate.checkAdmin(user);
//        if (check != null) {
//            return check;
//        } 
//        return attachmentService.delete(id);
//    }
}
