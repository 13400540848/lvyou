package org.ume.school.modules.user.money.recharge;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ume.school.modules.model.ErrorTag;
import org.ume.school.modules.model.entity.UserMoneyRecharge;
import org.ume.school.modules.model.enums.CheckStatus;
import org.ume.school.modules.model.enums.MoneyLogType;
import org.ume.school.modules.user.money.UserMoneyService;
import org.ume.school.modules.user.money.log.UserMoneyLogService;

import com.bluesimon.wbf.AdminLogined;
import com.bluesimon.wbf.IUser;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.utils.AuthValidate;

/**
 * Created by Zz on 2018/9/13.
 */
@RestController
@RequestMapping("/v0.1/admin/user/money/recharge")
public class UserMoneyRechargeAdminController {

    @Resource
    private UserMoneyRechargeService userMoneyRechargeService;
    
    @Resource
    private UserMoneyService userMoneyService;
    
    @Resource
    private UserMoneyLogService userMoneyLogService;
    
    /**
     * 获取充值记录
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Response<List<UserMoneyRecharge>> getList(@RequestParam(value = "checkStatus", required = false) Integer checkStatus,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "orderId", required = false) String orderId,            
            @RequestParam(value = "offset", required = true) String offset,
            @RequestParam(value = "limit", required = true) String limit, @AdminLogined IUser user) {
        Response<List<UserMoneyRecharge>> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        Response<List<UserMoneyRecharge>> result = new Response<List<UserMoneyRecharge>>();
        final UserMoneyRecharge query = new UserMoneyRecharge();
        query.setCheckStatus(checkStatus);
        query.setUserId(userId);
        query.setOrderId(orderId);
        Page<UserMoneyRecharge> projects = userMoneyRechargeService.findAll(query, offset, limit);
        result.setTotal(projects.getTotalElements());
        result.setRows(userMoneyRechargeService.parse(projects.getContent()));
        return result;
    }
    
    /**
     * 充值审核
     */
     @RequestMapping(value = "", method = RequestMethod.POST)
     public Response<UserMoneyRecharge> submit(@RequestBody UserMoneyRecharge model, @AdminLogined IUser user) {
         Response<UserMoneyRecharge> check = AuthValidate.checkAdmin(user);
         if (check != null) {
             return check;
         }
         Response<UserMoneyRecharge> resp = new Response<>();
         
         //参数错误
         if(model.getId().isEmpty() || model.getCheckStatus()==null || (model.getCheckStatus().intValue()!=CheckStatus.AGREE.getValue() && model.getCheckStatus().intValue()!=CheckStatus.NOT_AGREE.getValue())){
             resp.setResultMsg(ErrorTag.PARAM_NULL.getMessage());
             resp.setResultCode(ErrorTag.PARAM_NULL.getCode());
             return resp;
         }
         //充值查询
         UserMoneyRecharge modelDb = userMoneyRechargeService.get(model.getId());
         if(modelDb==null){
             resp.setResultMsg(ErrorTag.USER_MONEY_RECHARGE_NOT_EXISTS.getMessage());
             resp.setResultCode(ErrorTag.USER_MONEY_RECHARGE_NOT_EXISTS.getCode());
             return resp;
         }
         //保存充值数据
         modelDb.setCheckStatus(model.getCheckStatus().intValue());
         modelDb.setCheckReason(model.getCheckReason());
         modelDb.setCheckTime(new Date());
//         modelDb.setAdminId(user.getId());
//         modelDb.setAdminName(user.getUserName());
         userMoneyRechargeService.submit(modelDb);
         
         //通过给用户钱包加金额，同时增加用户的交易记录
         if(model.getCheckStatus().intValue() == CheckStatus.AGREE.getValue()){
             userMoneyService.save(modelDb.getUserId(), modelDb.getTypeId(), modelDb.getRealMoney(), MoneyLogType.RECHARGE, user);
         }
         
         resp.setRows(model);
         resp.setTotal(1);
         return resp;
     }
}
