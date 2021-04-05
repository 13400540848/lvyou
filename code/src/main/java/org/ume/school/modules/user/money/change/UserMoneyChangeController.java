package org.ume.school.modules.user.money.change;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ume.school.modules.model.ErrorTag;
import org.ume.school.modules.model.entity.MoneyType;
import org.ume.school.modules.model.entity.UserMoney;
import org.ume.school.modules.model.entity.UserMoneyChange;
import org.ume.school.modules.model.enums.MoneyLogType;
import org.ume.school.modules.model.enums.MoneyTypeMode;
import org.ume.school.modules.moneytype.MoneyTypeService;
import org.ume.school.modules.user.money.UserMoneyService;

import com.bluesimon.wbf.IUser;
import com.bluesimon.wbf.Logined;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.modules.user.UserService;
import com.bluesimon.wbf.utils.AuthValidate;

/**
 * Created by Zz on 2018/10/15.
 */
@RestController
@RequestMapping("/v0.1/user/money/change")
public class UserMoneyChangeController {

    @Resource
    private UserMoneyChangeService userMoneyChangeService;
        
    @Resource
    private MoneyTypeService moneyTypeService; 
    
    @Resource
    private UserService userService;
    
    @Resource
    private UserMoneyService userMoneyService;

    /**
     * 获取记录
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Response<List<UserMoneyChange>> getList(@RequestParam(value = "offset", required = true) String offset,
            @RequestParam(value = "limit", required = true) String limit, @Logined IUser user) {
        Response<List<UserMoneyChange>> check = AuthValidate.checkUser(user);
        if (check != null) {
            return check;
        }
        Response<List<UserMoneyChange>> result = new Response<List<UserMoneyChange>>();
        final UserMoneyChange query = new UserMoneyChange();
//        query.setUserId(user.getId());
        Page<UserMoneyChange> projects = userMoneyChangeService.findAll(query, offset, limit);
        result.setTotal(projects.getTotalElements());
        result.setRows(userMoneyChangeService.parse(projects.getContent()));
        return result;
    }
    
    /**
     * 获取所有列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Response<List<UserMoneyChange>> getList(@Logined IUser user) {
        Response<List<UserMoneyChange>> check = AuthValidate.checkUser(user);
        if (check != null) {
            return check;
        }
        Response<List<UserMoneyChange>> result = new Response<List<UserMoneyChange>>();
//        List<UserMoneyChange> list = userMoneyChangeService.findByUserId(user.getId());
//        result.setRows(list);
//        result.setTotal(list.isEmpty() ?  0 : list.size());
        return result;
    }
     
     /**
      * 我要兑换
      */
      @RequestMapping(value = "", method = RequestMethod.POST)
      public Response<UserMoneyChange> pay(@RequestBody UserMoneyChange model, @Logined IUser user, HttpServletRequest request) {
          Response<UserMoneyChange> check = AuthValidate.checkUser(user);
          if (check != null) {
              return check;
          }
          Response<UserMoneyChange> resp = new Response<>();
          
          //参数错误
          if(model.getChangeType() < 0 || model.getChangeType() > 2 || model.getFromTypeId().isEmpty() ||  model.getFromMoney() < 0
                  || model.getToTypeId().isEmpty() ||  model.getToMoney() < 0){
              resp.setResultMsg(ErrorTag.PARAM_NULL.getMessage());
              resp.setResultCode(ErrorTag.PARAM_NULL.getCode());
              return resp;
          }
          MoneyType mtSystem = moneyTypeService.findBySystem();
          if(mtSystem==null){
              resp.setResultMsg(ErrorTag.MONEY_TYPE_NOT_EXISTS.getMessage());
              resp.setResultCode(ErrorTag.MONEY_TYPE_NOT_EXISTS.getCode());
              return resp;
          }
          if(model.getChangeType() == 1){
              //兑换为DG（目前仅支持代币兑换为DG）
              MoneyType mt = moneyTypeService.findById(model.getFromTypeId());
              if(mt==null || mt.getTypeMode() != MoneyTypeMode.MONEY.getValue()){
                  resp.setResultMsg(ErrorTag.MONEY_TYPE_NOT_EXISTS.getMessage());
                  resp.setResultCode(ErrorTag.MONEY_TYPE_NOT_EXISTS.getCode());
                  return resp;
              }
//              UserMoney umMoney = userMoneyService.findByUserIdAndTypeId(user.getId(), model.getFromTypeId());
//              if(model.getFromMoney() > umMoney.getMoney()){
//                  resp.setResultMsg(ErrorTag.USER_MONEY_CHANGE_NOT_ENOUGH.getMessage());
//                  resp.setResultCode(ErrorTag.USER_MONEY_CHANGE_NOT_ENOUGH.getCode());
//                  return resp;
//              }
              double toMoney = model.getFromMoney() * mt.getDgScale();
              if(toMoney < 0.01){
                  resp.setResultMsg(ErrorTag.USER_MONEY_CHANGE_NOT_MIN.getMessage());
                  resp.setResultCode(ErrorTag.USER_MONEY_CHANGE_NOT_MIN.getCode());
                  return resp;
              }
              model.setToTypeId(mtSystem.getTypeId());
              model.setToMoney(toMoney);
              //设置手续费和实际到账
              model.setBrokeMoney(model.getToMoney()*mt.getDgBrokePercent()/100);
              model.setRealMoney(model.getToMoney() - model.getBrokeMoney()); 
              if(model.getRealMoney() < 0.01){
                  resp.setResultMsg(ErrorTag.USER_MONEY_CHANGE_NOT_MIN.getMessage());
                  resp.setResultCode(ErrorTag.USER_MONEY_CHANGE_NOT_MIN.getCode());
                  return resp;
              }
          }else{
              //DG兑换为代币
              MoneyType mt = moneyTypeService.findById(model.getToTypeId());
              if(mt==null || mt.getTypeMode() != MoneyTypeMode.MONEY.getValue()){
                  resp.setResultMsg(ErrorTag.MONEY_TYPE_NOT_EXISTS.getMessage());
                  resp.setResultCode(ErrorTag.MONEY_TYPE_NOT_EXISTS.getCode());
                  return resp;
              }
//              UserMoney umDG = userMoneyService.findByUserIdAndTypeId(user.getId(), mtSystem.getTypeId());
//              if(model.getFromMoney() > umDG.getMoney()){
//                  resp.setResultMsg(ErrorTag.USER_MONEY_CHANGE_NOT_ENOUGH.getMessage());
//                  resp.setResultCode(ErrorTag.USER_MONEY_CHANGE_NOT_ENOUGH.getCode());
//                  return resp;
//              }
              double toMoney = model.getFromMoney() / mt.getDgScale();
              if(toMoney < 0.01){
                  resp.setResultMsg(ErrorTag.USER_MONEY_CHANGE_NOT_MIN.getMessage());
                  resp.setResultCode(ErrorTag.USER_MONEY_CHANGE_NOT_MIN.getCode());
                  return resp;
              }
              model.setFromTypeId(mtSystem.getTypeId());
              model.setToMoney(toMoney);
              //设置手续费和实际到账
              model.setBrokeMoney(model.getToMoney()*mt.getDgBrokePercent()/100);
              model.setRealMoney(model.getToMoney() - model.getBrokeMoney());
              if(model.getRealMoney() < 0.01){
                  resp.setResultMsg(ErrorTag.USER_MONEY_CHANGE_NOT_MIN.getMessage());
                  resp.setResultCode(ErrorTag.USER_MONEY_CHANGE_NOT_MIN.getCode());
                  return resp;
              }
          }
          model.setId(UUID.randomUUID().toString());
//          model.setUserId(user.getId());
          model.setCreateTime(new Date());
          userMoneyChangeService.submit(model);
          
          //保存用户钱包
//          userMoneyService.save(user.getId(), model.getFromTypeId(), -model.getFromMoney(), MoneyLogType.CHANGE, null);
//          userMoneyService.save(user.getId(), model.getToTypeId(), model.getRealMoney(), MoneyLogType.CHANGE, null);
          
          resp.setRows(model);
          resp.setTotal(1);
          return resp;
      }
}
