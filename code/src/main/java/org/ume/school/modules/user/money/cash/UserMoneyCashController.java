package org.ume.school.modules.user.money.cash;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.ume.school.modules.model.ErrorTag;
import org.ume.school.modules.model.entity.MoneyType;
import org.ume.school.modules.model.entity.UserMoney;
import org.ume.school.modules.model.entity.UserMoneyCash;
import org.ume.school.modules.model.enums.CheckStatus;
import org.ume.school.modules.moneytype.MoneyTypeService;
import org.ume.school.modules.user.money.UserMoneyService;

import com.bluesimon.wbf.IUser;
import com.bluesimon.wbf.Logined;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.modules.user.UserEntity;
import com.bluesimon.wbf.modules.user.UserService;
import com.bluesimon.wbf.modules.user.enums.UserCheckStatusEnum;
import com.bluesimon.wbf.utils.AuthValidate;
import com.bluesimon.wbf.utils.MD5Util;
import com.bluesimon.wbf.utils.RandomUtil;

/**
 * Created by Zz on 2018/9/12.
 */
@RestController
@RequestMapping("/v0.1/user/money/cash")
public class UserMoneyCashController {

    @Resource
    private UserMoneyCashService userMoneyCashService;
    
    @Resource
    private UserMoneyService userMoneyService;
    
    @Resource
    private UserService userService;    
    
    @Resource
    private MoneyTypeService moneyTypeService;    

    /**
     * 获取列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Response<List<UserMoneyCash>> getList(@Logined IUser user) {
        Response<List<UserMoneyCash>> check = AuthValidate.checkUser(user);
        if (check != null) {
            return check;
        }
        Response<List<UserMoneyCash>> result = new Response<List<UserMoneyCash>>();
//        List<UserMoneyCash> list = userMoneyCashService.findByUserId(user.getId());
//        result.setRows(list);
//        result.setTotal(list.isEmpty() ?  0 : list.size());
        return result;
    }
    
    /**
     * 我要提现
     */
     @RequestMapping(value = "", method = RequestMethod.POST)
     public Response<UserMoneyCash> submit(@RequestBody UserMoneyCash model, @Logined IUser user, HttpServletRequest request) {
         Response<UserMoneyCash> check = AuthValidate.checkUser(user);
         if (check != null) {
             return check;
         }
         Response<UserMoneyCash> resp = new Response<>();
         
         //参数错误
         if(model.getTypeId().isEmpty() || model.getMoney() <= 0 || model.getDealPassword().isEmpty() || model.getSmsCode().isEmpty()){
             resp.setResultMsg(ErrorTag.PARAM_NULL.getMessage());
             resp.setResultCode(ErrorTag.PARAM_NULL.getCode());
             return resp;
         }
         
         //用户是否已认证
         UserEntity u = userService.getUser(user.getId());
         if(u.getStatus() != UserCheckStatusEnum.NORMAL.getValue()){
             resp.setResultMsg(ErrorTag.USER_NOT_CHECKED.getMessage());
             resp.setResultCode(ErrorTag.USER_NOT_CHECKED.getCode());
             return resp;
         }
         
         //币种错误
         MoneyType mt = moneyTypeService.findById(model.getTypeId());
         if(mt==null){
             resp.setResultMsg(ErrorTag.MONEY_TYPE_NOT_EXISTS.getMessage());
             resp.setResultCode(ErrorTag.MONEY_TYPE_NOT_EXISTS.getCode());
             return resp;
         } 
         //小于最低数量
         if(model.getMoney() < mt.getCashMin()){
             resp.setResultMsg(ErrorTag.USER_MONEY_CASH_MIN.getMessage());
             resp.setResultCode(ErrorTag.USER_MONEY_CASH_MIN.getCode());
             return resp;
         }
//         //您还有提现申请待管理员审核
//         List<UserMoneyCash>umrList = userMoneyCashService.findByUserIdAndCheckStatus(user.getId(), CheckStatus.UN_CHECK.getValue());
//         if(!umrList.isEmpty()){
//             resp.setResultMsg(ErrorTag.USER_MONEY_CASH_EXISTS.getMessage());
//             resp.setResultCode(ErrorTag.USER_MONEY_CASH_EXISTS.getCode());
//             return resp;
//         }         
         //余额不足
//         UserMoney um = userMoneyService.findByUserIdAndTypeId(user.getId(), model.getTypeId());
//         if(um==null || um.getMoney()<=0 || um.getMoney() < model.getMoney()){
//             resp.setResultMsg(ErrorTag.USER_MONEY_CASH_NOT_ENOUGH.getMessage());
//             resp.setResultCode(ErrorTag.USER_MONEY_CASH_NOT_ENOUGH.getCode());
//             return resp;
//         }
       //手机验证码
         Object smsCode = request.getSession().getAttribute(IUser.SMS_CODE);
         if (smsCode == null || smsCode.toString() == null) {
             resp.setResultCode(ErrorTag.USER_SMS_CODE_NULL.getCode());
             resp.setResultMsg(ErrorTag.USER_SMS_CODE_NULL.getMessage());
             return resp;
         }
         if(!model.getSmsCode().equals(smsCode.toString())){
             resp.setResultCode(ErrorTag.USER_SMS_CODE_ERROR.getCode());
             resp.setResultMsg(ErrorTag.USER_SMS_CODE_ERROR.getMessage());
             return resp;
         }
         //交易密码
//         if(!u.getDealPassword().equals(MD5Util.getMD5(model.getDealPassword()))){
//             resp.setResultCode(ErrorTag.USER_DEAL_PASSWORD_ERROR.getCode());
//             resp.setResultMsg(ErrorTag.USER_DEAL_PASSWORD_ERROR.getMessage());
//             return resp;
//         }
         //设置手续费和实际到账
         model.setBrokeMoney(model.getMoney()*mt.getCashBrokePercent()/100);
         model.setRealMoney(model.getMoney() - model.getBrokeMoney());
         
         //保存用户提现
         model.setId(UUID.randomUUID().toString());
         model.setOrderId(RandomUtil.createOrderId());
//         model.setUserId(user.getId());
         model.setCreateTime(new Date());
         model.setCheckStatus(CheckStatus.UN_CHECK.getValue());
         userMoneyCashService.submit(model);
         
         //冻结用户提现的钱包金额
//         um.setMoney(um.getMoney() - model.getMoney());
//         userMoneyService.submit(um);
         
         resp.setRows(model);
         resp.setTotal(1);
         resp.setResultMsg(model.getOrderId());
         return resp;
     }
}
