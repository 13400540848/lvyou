package org.ume.school.modules.user.money.cash;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ume.school.modules.model.ErrorTag;
import org.ume.school.modules.model.entity.UserMoney;
import org.ume.school.modules.model.entity.UserMoneyCash;
import org.ume.school.modules.model.entity.UserMoneyLog;
import org.ume.school.modules.model.enums.CheckStatus;
import org.ume.school.modules.model.enums.MoneyLogType;
import org.ume.school.modules.user.money.UserMoneyService;
import org.ume.school.modules.user.money.log.UserMoneyLogService;

import com.bluesimon.wbf.AdminLogined;
import com.bluesimon.wbf.IUser;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.utils.AuthValidate;

/**
 * Created by Zz on 2018/9/3.
 */
@RestController
@RequestMapping("/v0.1/admin/user/money/cash")
public class UserMoneyCashAdminController {

    @Resource
    private UserMoneyCashService userMoneyCashService;
    
    @Resource
    private UserMoneyService userMoneyService;
    
    @Resource
    private UserMoneyLogService userMoneyLogService;
    
    /**
     * 获取用户提现记录
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Response<List<UserMoneyCash>> getList(@RequestParam(value = "checkStatus", required = false) Integer checkStatus,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "orderId", required = false) String orderId,            
            @RequestParam(value = "offset", required = true) String offset,
            @RequestParam(value = "limit", required = true) String limit, @AdminLogined IUser user) {
        Response<List<UserMoneyCash>> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        Response<List<UserMoneyCash>> result = new Response<List<UserMoneyCash>>();
        final UserMoneyCash query = new UserMoneyCash();
        query.setCheckStatus(checkStatus);
        query.setUserId(userId);
        query.setOrderId(orderId);
        Page<UserMoneyCash> projects = userMoneyCashService.findAll(query, offset, limit);
        result.setTotal(projects.getTotalElements());
        result.setRows(userMoneyCashService.parse(projects.getContent()));
        return result;
    }
    
    /**
     * 提现审核
     */
     @RequestMapping(value = "", method = RequestMethod.POST)
     public Response<UserMoneyCash> submit(@RequestBody UserMoneyCash model, @AdminLogined IUser user) {
         Response<UserMoneyCash> check = AuthValidate.checkAdmin(user);
         if (check != null) {
             return check;
         }
         Response<UserMoneyCash> resp = new Response<>();
         
         //参数错误
         if(model.getId().isEmpty() || model.getCheckStatus()==null || (model.getCheckStatus().intValue()!=CheckStatus.AGREE.getValue() && model.getCheckStatus().intValue()!=CheckStatus.NOT_AGREE.getValue())){
             resp.setResultMsg(ErrorTag.PARAM_NULL.getMessage());
             resp.setResultCode(ErrorTag.PARAM_NULL.getCode());
             return resp;
         }
         //提现查询
         UserMoneyCash modelDb = userMoneyCashService.get(model.getId());
         if(modelDb==null){
             resp.setResultMsg(ErrorTag.USER_MONEY_CASH_NOT_EXISTS.getMessage());
             resp.setResultCode(ErrorTag.USER_MONEY_CASH_NOT_EXISTS.getCode());
             return resp;
         }
         //保存提现数据
         modelDb.setCheckStatus(model.getCheckStatus().intValue());
         modelDb.setCheckReason(model.getCheckReason());
         modelDb.setCheckTime(new Date());
//         modelDb.setAdminId(user.getId());
//         modelDb.setAdminName(user.getUserName());
         userMoneyCashService.submit(modelDb);
         
         //用户钱包
         UserMoney um = userMoneyService.findByUserIdAndTypeId(modelDb.getUserId(), modelDb.getTypeId());
         if(um == null){
             um = new UserMoney();
             um.setId(UUID.randomUUID().toString());
             um.setMoney((double)0);
             um.setTypeId(modelDb.getTypeId());
             um.setUserId(modelDb.getUserId());
             userMoneyService.submit(um);
         }
         //不通过返还用户钱包加金额，同时增加用户的交易记录
         if(model.getCheckStatus().intValue() == CheckStatus.NOT_AGREE.getValue()){
             um.setMoney(um.getMoney() + modelDb.getMoney());
             userMoneyService.submit(um);
         }else{
             UserMoneyLog uml = new UserMoneyLog();
             uml.setAfterMoney(um.getMoney());
             uml.setBeforeMoney(um.getMoney() + modelDb.getMoney());
             uml.setCreateTime(new Date());
             uml.setId(UUID.randomUUID().toString());
             uml.setLogType(MoneyLogType.CASH.getValue());
             uml.setMoney(-modelDb.getMoney());
             uml.setTypeId(modelDb.getTypeId());
             uml.setUserId(modelDb.getUserId());
//             uml.setAdminId(user.getId());
//             uml.setAdminName(user.getUserName());
             userMoneyLogService.submit(uml);
         }
         
         resp.setRows(model);
         resp.setTotal(1);
         return resp;
     }
   
}
