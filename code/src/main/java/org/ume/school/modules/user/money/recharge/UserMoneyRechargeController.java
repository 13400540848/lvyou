package org.ume.school.modules.user.money.recharge;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.ume.school.modules.model.ErrorTag;
import org.ume.school.modules.model.entity.MoneyType;
import org.ume.school.modules.model.entity.UserMoneyRecharge;
import org.ume.school.modules.model.enums.UserMoneyRechargeStatus;
import org.ume.school.modules.moneytype.MoneyTypeService;

import com.bluesimon.wbf.IUser;
import com.bluesimon.wbf.Logined;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.modules.user.UserEntity;
import com.bluesimon.wbf.modules.user.UserService;
import com.bluesimon.wbf.utils.AuthValidate;
import com.bluesimon.wbf.utils.MD5Util;
import com.bluesimon.wbf.utils.RandomUtil;
import com.bluesimon.wbf.view.VerificationCodeController;

/**
 * Created by Zz on 2018/9/12.
 */
@RestController
@RequestMapping("/v0.1/user/money/recharge")
public class UserMoneyRechargeController {

    @Resource
    private UserMoneyRechargeService userMoneyRechargeService;
        
    @Resource
    private MoneyTypeService moneyTypeService; 
    
    @Resource
    private UserService userService;

    /**
     * 获取列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Response<List<UserMoneyRecharge>> getList(@Logined IUser user) {
        Response<List<UserMoneyRecharge>> check = AuthValidate.checkUser(user);
        if (check != null) {
            return check;
        }
        Response<List<UserMoneyRecharge>> result = new Response<List<UserMoneyRecharge>>();
//        List<UserMoneyRecharge> list = userMoneyRechargeService.findByUserId(user.getId());
//        result.setRows(list);
//        result.setTotal(list.isEmpty() ?  0 : list.size());
        return result;
    }
    
    /**
     * 获取订单
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<UserMoneyRecharge> getInfo(@PathVariable("id") String id, @Logined IUser user) {
        Response<UserMoneyRecharge> check = AuthValidate.checkUser(user);
        if (check != null) {
            return check;
        }
        Response<UserMoneyRecharge> resp = new Response<UserMoneyRecharge>();
        UserMoneyRecharge model = userMoneyRechargeService.get(id);
        if(model==null || !model.getUserId().equals(user.getId())){
            resp.setResultMsg(ErrorTag.PARAM_NULL.getMessage());
            resp.setResultCode(ErrorTag.PARAM_NULL.getCode());
            return resp;
        }
        resp.setRows(model);
        resp.setTotal(1);
        return resp;
    }
    
    /**
     * 我要充值
     */
     @RequestMapping(value = "", method = RequestMethod.POST)
     public Response<UserMoneyRecharge> submit(@RequestBody UserMoneyRecharge model, @Logined IUser user, HttpServletRequest request) {
         Response<UserMoneyRecharge> check = AuthValidate.checkUser(user);
         if (check != null) {
             return check;
         }
         Response<UserMoneyRecharge> resp = new Response<>();
         
         //参数错误
         if(model.getTypeId().isEmpty() || model.getMoney()==null || model.getMoney().floatValue() <= 0){
             resp.setResultMsg(ErrorTag.PARAM_NULL.getMessage());
             resp.setResultCode(ErrorTag.PARAM_NULL.getCode());
             return resp;
         }
         //验证码错误
         String resultCode = (String) request.getSession().getAttribute(VerificationCodeController.SESSION_KEY_OF_RAND_CODE);
         if(!resultCode.toLowerCase().equals(model.getValidateCode().toLowerCase())){
             resp.setResultCode(ErrorTag.VALEDATE_CODE_ERROR.getCode());
             resp.setResultMsg(ErrorTag.VALEDATE_CODE_ERROR.getMessage());
             return resp;
         }
         //交易密码错误
//         UserEntity u = userService.getUser(user.getId());
//         if (!u.getDealPassword().equals(MD5Util.getMD5(model.getDealPassword()))) {
//             resp.setResultCode(ErrorTag.USER_DEAL_PASSWORD_ERROR.getCode());
//             resp.setResultMsg(ErrorTag.USER_DEAL_PASSWORD_ERROR.getMessage());
//             return resp;
//         }
         //币种错误
         MoneyType mt = moneyTypeService.findById(model.getTypeId());
         if(mt==null){
             resp.setResultMsg(ErrorTag.MONEY_TYPE_NOT_EXISTS.getMessage());
             resp.setResultCode(ErrorTag.MONEY_TYPE_NOT_EXISTS.getCode());
             return resp;
         }
         
         //您还有充值申请待支付
//         List<UserMoneyRecharge>umrList = userMoneyRechargeService.findByUserIdAndCheckStatus(user.getId(), UserMoneyRechargeStatus.PAY_WAIT.getValue());
//         List<UserMoneyRecharge>umrList1 = userMoneyRechargeService.findByUserIdAndCheckStatus(user.getId(), UserMoneyRechargeStatus.FAILTRUE.getValue());
//         if(!umrList.isEmpty() || !umrList1.isEmpty()){
//             resp.setResultMsg(ErrorTag.USER_MONEY_RECHARGE_EXISTS.getMessage());
//             resp.setResultCode(ErrorTag.USER_MONEY_RECHARGE_EXISTS.getCode());
//             return resp;
//         }
         
         //设置手续费和实际到账
         model.setBrokeMoney(model.getMoney()*mt.getRechargeBrokePercent()/100);
         model.setRealMoney(model.getMoney() - model.getBrokeMoney());
         
         //保存用户充值
         model.setId(UUID.randomUUID().toString());
         model.setOrderId(RandomUtil.createOrderId());
//         model.setUserId(user.getId());
         model.setCreateTime(new Date());
         model.setCheckStatus(UserMoneyRechargeStatus.PAY_WAIT.getValue());
         userMoneyRechargeService.submit(model);
         
         resp.setRows(model);
         resp.setTotal(1);
         resp.setResultMsg(model.getOrderId());
         return resp;
     }
     
     /**
      * 我要支付
      */
      @RequestMapping(value = "/pay", method = RequestMethod.POST)
      public Response<UserMoneyRecharge> pay(@RequestBody UserMoneyRecharge model, @Logined IUser user, HttpServletRequest request) {
          Response<UserMoneyRecharge> check = AuthValidate.checkUser(user);
          if (check != null) {
              return check;
          }
          Response<UserMoneyRecharge> resp = new Response<>();
          
          //参数错误
          if(model.getId().isEmpty()){
              resp.setResultMsg(ErrorTag.PARAM_NULL.getMessage());
              resp.setResultCode(ErrorTag.PARAM_NULL.getCode());
              return resp;
          }
          //充值记录
          UserMoneyRecharge modelDb =  userMoneyRechargeService.get(model.getId());
          if(modelDb==null || !modelDb.getUserId().equals(user.getId())){
              resp.setResultMsg(ErrorTag.USER_MONEY_RECHARGE_NOT_EXISTS.getMessage());
              resp.setResultCode(ErrorTag.USER_MONEY_RECHARGE_NOT_EXISTS.getCode());
              return resp;
          }
          if(modelDb.getCheckStatus().intValue()!=UserMoneyRechargeStatus.PAY_WAIT.getValue() && modelDb.getCheckStatus().intValue()!=UserMoneyRechargeStatus.FAILTRUE.getValue()){
              resp.setResultMsg(ErrorTag.USER_MONEY_RECHARGE_NOT_EXISTS.getMessage());
              resp.setResultCode(ErrorTag.USER_MONEY_RECHARGE_NOT_EXISTS.getCode());
              return resp;
          }
          //保存用户支付
          model.setCheckStatus(UserMoneyRechargeStatus.SEND_WAIT.getValue());
          userMoneyRechargeService.submit(model);
          
          resp.setRows(model);
          resp.setTotal(1);
          return resp;
      }
      
      /**
       * 我要取消
       */
       @RequestMapping(value = "/cancle", method = RequestMethod.POST)
       public Response<UserMoneyRecharge> cancle(@RequestBody UserMoneyRecharge model, @Logined IUser user, HttpServletRequest request) {
           Response<UserMoneyRecharge> check = AuthValidate.checkUser(user);
           if (check != null) {
               return check;
           }
           Response<UserMoneyRecharge> resp = new Response<>();
           
           //参数错误
           if(model.getId().isEmpty()){
               resp.setResultMsg(ErrorTag.PARAM_NULL.getMessage());
               resp.setResultCode(ErrorTag.PARAM_NULL.getCode());
               return resp;
           }
           //充值记录
           UserMoneyRecharge modelDb =  userMoneyRechargeService.get(model.getId());
           if(modelDb==null || !modelDb.getUserId().equals(user.getId())){
               resp.setResultMsg(ErrorTag.USER_MONEY_RECHARGE_NOT_EXISTS.getMessage());
               resp.setResultCode(ErrorTag.USER_MONEY_RECHARGE_NOT_EXISTS.getCode());
               return resp;
           }
           if(modelDb.getCheckStatus().intValue()!=UserMoneyRechargeStatus.PAY_WAIT.getValue() && modelDb.getCheckStatus().intValue()!=UserMoneyRechargeStatus.FAILTRUE.getValue()){
               resp.setResultMsg(ErrorTag.USER_MONEY_RECHARGE_NOT_EXISTS.getMessage());
               resp.setResultCode(ErrorTag.USER_MONEY_RECHARGE_NOT_EXISTS.getCode());
               return resp;
           }
           //保存用户取消
           model.setCheckStatus(UserMoneyRechargeStatus.CANCLE.getValue());
           userMoneyRechargeService.submit(model);
           
           resp.setRows(model);
           resp.setTotal(1);
           return resp;
       }
}
