package org.ume.school.modules.user.money.sell;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ume.school.modules.model.ErrorTag;
import org.ume.school.modules.model.entity.MoneyType;
import org.ume.school.modules.model.entity.UserMoney;
import org.ume.school.modules.model.entity.UserMoneySell;
import org.ume.school.modules.model.enums.MoneyLogType;
import org.ume.school.modules.model.enums.SellStatus;
import org.ume.school.modules.moneytype.MoneyTypeService;
import org.ume.school.modules.user.money.UserMoneyService;

import com.bluesimon.wbf.IUser;
import com.bluesimon.wbf.Logined;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.utils.AuthValidate;

/**
 * Created by Zz on 2018/9/16.
 */
@RestController
@RequestMapping("/v0.1/user/money/sell")
public class UserMoneySellController {

    @Resource
    private UserMoneySellService userMoneySellService;
    
    @Resource
    private UserMoneyService userMoneyService;
    
    @Resource
    private MoneyTypeService moneyTypeService;    

    /**
     * 获取卖单列表（状态正常、排除自己）
     */
    @RequestMapping(value = "/other", method = RequestMethod.GET)
    public Response<List<UserMoneySell>> getList(
            @RequestParam(value = "typeId", required = false) String typeId,
            @RequestParam(value = "offset", required = true) String offset,
            @RequestParam(value = "limit", required = true) String limit, @Logined IUser user) {
        Response<List<UserMoneySell>> check = AuthValidate.checkUser(user);
        if (check != null) {
            return check;
        }
        Response<List<UserMoneySell>> result = new Response<List<UserMoneySell>>();
        final UserMoneySell query = new UserMoneySell();
//        query.setExcludeUserId(user.getId());
        query.setTypeId(typeId);
        query.setStatus(SellStatus.NORMAL.getValue());
        Page<UserMoneySell> list = userMoneySellService.findAll(query, offset, limit);
        result.setTotal(list.getTotalElements());
        result.setRows(userMoneySellService.parse(list.getContent()));
        return result;
    }
    
    /**
     * 获取我的卖单列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Response<List<UserMoneySell>> getMineList(@RequestParam(value = "offset", required = true) String offset,
            @RequestParam(value = "limit", required = true) String limit, @Logined IUser user) {
        Response<List<UserMoneySell>> check = AuthValidate.checkUser(user);
        if (check != null) {
            return check;
        }
        Response<List<UserMoneySell>> result = new Response<List<UserMoneySell>>();
        final UserMoneySell query = new UserMoneySell();
//        query.setUserId(user.getId());
        Page<UserMoneySell> list = userMoneySellService.findAll(query, offset, limit);
        result.setTotal(list.getTotalElements());
        result.setRows(userMoneySellService.parse(list.getContent()));
        return result;
    }
    
    /**
     * 获取卖单详情
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<UserMoneySell> getInfo(@PathVariable("id") String id, @Logined IUser user) {
        Response<UserMoneySell> check = AuthValidate.checkUser(user);
        if (check != null) {
            return check;
        }
        Response<UserMoneySell> result = new Response<UserMoneySell>();
        UserMoneySell model = userMoneySellService.get(id);
        result.setTotal(model!=null ? 1 : 0);
        result.setRows(userMoneySellService.parse(model));
        return result;
    }
    
    
    /**
     * 我要卖
     */
     @RequestMapping(value = "", method = RequestMethod.POST)
     public Response<UserMoneySell> submit(@RequestBody UserMoneySell model, @Logined IUser user) {
         Response<UserMoneySell> check = AuthValidate.checkUser(user);
         if (check != null) {
             return check;
         }
         Response<UserMoneySell> resp = new Response<>();
         
         //参数错误
         if(model.getTypeId()==null || model.getBuyTypeId()==null || model.getMoney() <= 0){
             resp.setResultMsg(ErrorTag.PARAM_NULL.getMessage());
             resp.setResultCode(ErrorTag.PARAM_NULL.getCode());
             return resp;
         }
         //币种参数错误
         if(model.getTypeId().equals(model.getBuyTypeId())){
             resp.setResultMsg(ErrorTag.USER_MONEY_SELL_TYPE_ERROR.getMessage());
             resp.setResultCode(ErrorTag.USER_MONEY_SELL_TYPE_ERROR.getCode());
             return resp;
         }
         //币种错误
         MoneyType mt = moneyTypeService.findById(model.getTypeId());
         if(mt==null){
             resp.setResultMsg(ErrorTag.MONEY_TYPE_NOT_EXISTS.getMessage());
             resp.setResultCode(ErrorTag.MONEY_TYPE_NOT_EXISTS.getCode());
             return resp;
         }
         MoneyType mt1 = moneyTypeService.findById(model.getBuyTypeId());
         if(mt1==null){
             resp.setResultMsg(ErrorTag.MONEY_TYPE_NOT_EXISTS.getMessage());
             resp.setResultCode(ErrorTag.MONEY_TYPE_NOT_EXISTS.getCode());
             return resp;
         }
         
         //余额不足
//         UserMoney um = userMoneyService.findByUserIdAndTypeId(user.getId(), model.getTypeId());
//         if(um==null || um.getMoney()<=0 || um.getMoney() < model.getMoney()){
//             resp.setResultMsg(ErrorTag.USER_MONEY_CASH_NOT_ENOUGH.getMessage());
//             resp.setResultCode(ErrorTag.USER_MONEY_CASH_NOT_ENOUGH.getCode());
//             return resp;
//         }
         
         //保存用户卖
         model.setId(UUID.randomUUID().toString());
//         model.setUserId(user.getId());
         model.setCreateTime(new Date());
         model.setStatus(SellStatus.NORMAL.getValue());
         model.setLeaveMoney(model.getMoney());
         userMoneySellService.submit(model);
         
         //冻结用户卖的钱包金额
//         um.setMoney(um.getMoney() - model.getMoney());
//         userMoneyService.submit(um);
         
         resp.setRows(model);
         resp.setTotal(1);
         return resp;
     }
     
     /**
      * 我要撤销卖单
      */
      @RequestMapping(value = "/cancel", method = RequestMethod.POST)
      public Response<UserMoneySell> remove(@RequestBody UserMoneySell model, @Logined IUser user) {
          Response<UserMoneySell> check = AuthValidate.checkUser(user);
          if (check != null) {
              return check;
          }
          Response<UserMoneySell> resp = new Response<>();
          
          //参数错误
          if(model.getId()==null){
              resp.setResultMsg(ErrorTag.PARAM_NULL.getMessage());
              resp.setResultCode(ErrorTag.PARAM_NULL.getCode());
              return resp;
          }
          UserMoneySell modelDb = userMoneySellService.get(model.getId());
          if(modelDb.getStatus()==null || modelDb.getStatus().intValue() != SellStatus.NORMAL.getValue()){
              resp.setResultMsg(ErrorTag.USER_MONEY_SELL_CLOSE.getMessage());
              resp.setResultCode(ErrorTag.USER_MONEY_SELL_CLOSE.getCode());
              return resp;
          }
          if(!modelDb.getUserId().equals(user.getId())){
              resp.setResultMsg(ErrorTag.USER_MONEY_SELL_NOT_USER.getMessage());
              resp.setResultCode(ErrorTag.USER_MONEY_SELL_NOT_USER.getCode());
              return resp;
          }
          
          //保存用户卖
          modelDb.setStatus(SellStatus.CANCEL.getValue());
          userMoneySellService.submit(modelDb);
          
          //返回剩余金额
//          userMoneyService.save(user.getId(), modelDb.getTypeId(), modelDb.getLeaveMoney(), MoneyLogType.SELL_CANCEL, null);
          
          resp.setRows(model);
          resp.setTotal(1);
          return resp;
      }
}
