package com.bluesimon.wbf.modules.dict.item;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
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
import com.bluesimon.wbf.Logined;
import com.bluesimon.wbf.RequestPager;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.ResponseErrorEnum;
import com.bluesimon.wbf.modules.dict.DictEntity;
import com.bluesimon.wbf.modules.dict.DictService;
import com.bluesimon.wbf.utils.AuthValidate;
import com.bluesimon.wbf.utils.StringUtil;

/**
 * Created by Zz on 2021/3/21.
 */
@RestController
@RequestMapping("/v0.1/admin/dict/item")
public class DictItemAdminController {

    @Resource
    private DictItemService dictItemService;
    
    @Resource
    private DictService dictService;
    
    /**
     * 获取字典项列表(多个Key)
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Response<Dictionary<String, List<DictItemEntity>>> getItemsByCodes(@RequestBody(required = true) List<String> codes, @AdminLogined IUser user) {
        Dictionary<String, List<DictItemEntity>> result = null;
        Response<Dictionary<String, List<DictItemEntity>>> check = AuthValidate.checkAdmin(user);
        if(check!=null){
            return check;
        }
        if(codes == null || codes.isEmpty()){
            return new Response<>();
        }
        List<DictEntity> dicts = dictService.getByCodes(codes);
        if(dicts!=null &&!dicts.isEmpty()){
            result = new Hashtable<String, List<DictItemEntity>>();
            final List<Long> dictIds = new ArrayList<>();
            for(DictEntity d : dicts){
                dictIds.add(d.getId());
            }
            List<DictItemEntity> rows = dictItemService.getByDictIds(dictIds);
            if(!StringUtil.isEmpty(rows)){
                for(DictEntity d : dicts){
                    List<DictItemEntity> items = new ArrayList<DictItemEntity>();
                    for(DictItemEntity item : rows){
                        if(item.getDictId().equals(d.getId())){
                            items.add(item);
                        }
                    }
                    result.put(d.getCode(), items);
                }
            }
            return new Response<>(result);
        }
        return new Response<>();
    }
    
    /**
     * 查询
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Response<List<DictItemEntity>> getAdminMenus(@RequestBody RequestPager<DictItemEntity> req, @AdminLogined IUser user) {
        Response<List<DictItemEntity>> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        Response<List<DictItemEntity>> result = new Response<List<DictItemEntity>>();
        Page<DictItemEntity> projects = dictItemService.findAll(req);
        result.setTotal(projects.getTotalElements());
        result.setRows(projects.getContent());
        return result;
    }
    
    /**
     * 详情
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<DictItemEntity> get(@PathVariable("id") Long id, @AdminLogined IUser user) {
        Response<DictItemEntity> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        } 
        return dictItemService.get(id);
    }
    
    /**
     * 保存
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Response<DictItemEntity> submit(@RequestBody(required = true) DictItemEntity entity, @AdminLogined IUser user) {
        Response<DictItemEntity> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        if(StringUtil.isEmpty(entity.getName()) || StringUtil.isEmpty(entity.getCode())){
            return new Response<>(ResponseErrorEnum.PARAM_NULL);
        }
        if (StringUtil.isEmpty(entity.getId())) { //新增
            return dictItemService.add(entity);
        }else{
            return dictItemService.edit(entity);
        }
    }
    
    /**
     * 删除
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Response<DictItemEntity> delete(@PathVariable("id") Long id, @AdminLogined IUser user) {
        Response<DictItemEntity> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        } 
        return dictItemService.delete(id);
    }
}
