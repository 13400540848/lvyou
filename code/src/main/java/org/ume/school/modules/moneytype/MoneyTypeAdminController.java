package org.ume.school.modules.moneytype;

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
import org.ume.school.modules.model.entity.MoneyType;
import org.ume.school.modules.model.entity.ProjectMoney;
import org.ume.school.modules.project.money.ProjectMoneyService;

import com.bluesimon.wbf.AdminLogined;
import com.bluesimon.wbf.IUser;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.utils.AuthValidate;

/**
 * Created by Zz on 2018/8/30.
 */
@RestController
@RequestMapping("/v0.1/admin/moneytype")
public class MoneyTypeAdminController {

    @Resource
    private MoneyTypeService moneyTypeService;
    
    @Resource
    private ProjectMoneyService projectMoneyService;

    /**
     * 获取详情
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<MoneyType> getProjectById(@PathVariable("id") String id) {
        MoneyType model = moneyTypeService.findById(id);
        return new Response<>(model);
    }
    
    /**
     * 全部数据
     *
     * @param offset
     * @param limit
     * @param orderby
     * @param direction
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Response<List<MoneyType>> getAdminMoneyTypes(@AdminLogined IUser user) {
        Response<List<MoneyType>> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        List<MoneyType> list = moneyTypeService.findList();
        return new Response<>(list);
    }

    /**
    * 提交
    * @return
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Response<MoneyType> submitMoneyType(@RequestBody MoneyType money, @AdminLogined IUser user) {
        Response<MoneyType> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        Response<MoneyType> resp = new Response<>();
        if (StringUtils.isEmpty(money.getTypeId())) { //新增
            money.setTypeId(UUID.randomUUID().toString());
            resp.setRows(moneyTypeService.submitMoneyType(money));
        }
        else { // 修改
            MoneyType moneyDb = moneyTypeService.findById(money.getTypeId());
            if(moneyDb==null){
                resp.setResultCode(ErrorTag.MONEY_TYPE_NOT_EXISTS.getCode());
                resp.setResultMsg(ErrorTag.MONEY_TYPE_NOT_EXISTS.getMessage());
            }
            else{
                resp.setRows(moneyTypeService.submitMoneyType(money));
            }
        }
        return resp;
    }
    
    /**
     * 删除
     * @return
     */
     @RequestMapping(value = "/{type_id}", method = RequestMethod.DELETE)
     public Response<MoneyType> deleteMoneyType(@PathVariable("type_id") String id, @AdminLogined IUser user) {
         Response<MoneyType> check = AuthValidate.checkAdmin(user);
         if (check != null) {
             return check;
         }
         Response<MoneyType> resp = new Response<>();
         if (!StringUtils.isEmpty(id)) {
             MoneyType modelDb = moneyTypeService.findById(id);
             if(modelDb==null){
                 resp.setResultCode(ErrorTag.MONEY_TYPE_NOT_EXISTS.getCode());
                 resp.setResultMsg(ErrorTag.MONEY_TYPE_NOT_EXISTS.getMessage());
             }
             else{
                 List<ProjectMoney> list = projectMoneyService.findByTypeId(modelDb.getTypeId());
                 if(!list.isEmpty()){
                     resp.setResultCode(ErrorTag.MONEY_TYPE_USED.getCode());
                     resp.setResultMsg(ErrorTag.MONEY_TYPE_USED.getMessage());
                 }
                 else{
                     moneyTypeService.deleteMoneyType(modelDb);
                 }
             }
         }
         else { 
             resp.setResultCode(ErrorTag.PARAM_NULL.getCode());
             resp.setResultMsg(ErrorTag.PARAM_NULL.getMessage());
         }
         return resp;
     }
}
