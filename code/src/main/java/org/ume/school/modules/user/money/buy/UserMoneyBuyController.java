package org.ume.school.modules.user.money.buy;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.ume.school.modules.model.ErrorTag;
import org.ume.school.modules.model.entity.MoneyType;
import org.ume.school.modules.model.entity.UserMoney;
import org.ume.school.modules.model.entity.UserMoneyBuy;
import org.ume.school.modules.model.entity.UserMoneySell;
import org.ume.school.modules.model.enums.MoneyLogType;
import org.ume.school.modules.model.enums.SellStatus;
import org.ume.school.modules.moneytype.MoneyTypeService;
import org.ume.school.modules.user.money.UserMoneyService;
import org.ume.school.modules.user.money.log.UserMoneyLogService;
import org.ume.school.modules.user.money.sell.UserMoneySellService;

import com.bluesimon.wbf.IUser;
import com.bluesimon.wbf.Logined;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.utils.AuthValidate;

/**
 * Created by Zz on 2018/9/12.
 */
@RestController
@RequestMapping("/v0.1/user/money/buy")
public class UserMoneyBuyController {

    @Resource
    private UserMoneyBuyService userMoneyBuyService;
    
    @Resource
    private UserMoneySellService userMoneySellService;
    
    @Resource
    private UserMoneyService userMoneyService;
    
    @Resource
    private MoneyTypeService moneyTypeService;    
    
    @Resource
    private UserMoneyLogService userMoneyLogService;

    /**
     * 获取列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Response<List<UserMoneyBuy>> getList(@Logined IUser user) {
        Response<List<UserMoneyBuy>> check = AuthValidate.checkUser(user);
        if (check != null) {
            return check;
        }
        Response<List<UserMoneyBuy>> result = new Response<List<UserMoneyBuy>>();
//        List<UserMoneyBuy> list = userMoneyBuyService.findByUserId(user.getId());
//        result.setRows(list);
//        result.setTotal(list.isEmpty() ?  0 : list.size());
        return result;
    }
    
    /**
     * 我要买
     */
     @RequestMapping(value = "", method = RequestMethod.POST)
     public Response<UserMoneyBuy> submit(@RequestBody UserMoneyBuy model, @Logined IUser user) {
         Response<UserMoneyBuy> check = AuthValidate.checkUser(user);
         if (check != null) {
             return check;
         }
         Response<UserMoneyBuy> resp = new Response<>();
         
         //参数错误
         if(model.getSellId().isEmpty() || model.getMoney() <= 0){
             resp.setResultMsg(ErrorTag.PARAM_NULL.getMessage());
             resp.setResultCode(ErrorTag.PARAM_NULL.getCode());
             return resp;
         }
         
         //卖单不存在
         UserMoneySell ums = userMoneySellService.get(model.getSellId());
         if(ums == null){
             resp.setResultMsg(ErrorTag.USER_MONEY_SELL_NOT_EXISTS.getMessage());
             resp.setResultCode(ErrorTag.USER_MONEY_SELL_NOT_EXISTS.getCode());
             return resp;
         }
         //不能自己买卖
         if(ums.getUserId().equals(user.getId())){
             resp.setResultMsg(ErrorTag.USER_MONEY_SELL_MINE.getMessage());
             resp.setResultCode(ErrorTag.USER_MONEY_SELL_MINE.getCode());
             return resp;
         }
         //卖单已撤销
         if(ums.getStatus()== null || ums.getStatus().intValue() != SellStatus.NORMAL.getValue()){
             resp.setResultMsg(ErrorTag.USER_MONEY_SELL_CLOSE.getMessage());
             resp.setResultCode(ErrorTag.USER_MONEY_SELL_CLOSE.getCode());
             return resp;
         }
         //卖单剩余金额为0
         if(ums.getLeaveMoney() <= 0){
             resp.setResultMsg(ErrorTag.USER_MONEY_SELL_LEAVE_MONEY_ENOUGH.getMessage());
             resp.setResultCode(ErrorTag.USER_MONEY_SELL_LEAVE_MONEY_ENOUGH.getCode());
             return resp;
         }
        //超过卖单剩余金额
         if(model.getMoney() > ums.getLeaveMoney()){
             resp.setResultMsg(ErrorTag.USER_MONEY_SELL_LEAVE_MONEY_MORE.getMessage());
             resp.setResultCode(ErrorTag.USER_MONEY_SELL_LEAVE_MONEY_MORE.getCode());
             return resp;
         }
         
         //币种错误
         MoneyType mt = moneyTypeService.findById(ums.getTypeId());
         if(mt==null){
             resp.setResultMsg(ErrorTag.MONEY_TYPE_NOT_EXISTS.getMessage());
             resp.setResultCode(ErrorTag.MONEY_TYPE_NOT_EXISTS.getCode());
             return resp;
         }
         MoneyType mt1 = moneyTypeService.findById(ums.getBuyTypeId());
         if(mt1==null){
             resp.setResultMsg(ErrorTag.MONEY_TYPE_NOT_EXISTS.getMessage());
             resp.setResultCode(ErrorTag.MONEY_TYPE_NOT_EXISTS.getCode());
             return resp;
         }
         //余额不足
//         UserMoney um = userMoneyService.findByUserIdAndTypeId(user.getId(), ums.getBuyTypeId());
//         if(um.getMoney()<=0 || um.getMoney() < model.getMoney()){
//             resp.setResultMsg(ErrorTag.USER_MONEY_CASH_NOT_ENOUGH.getMessage());
//             resp.setResultCode(ErrorTag.USER_MONEY_CASH_NOT_ENOUGH.getCode());
//             return resp;
//         }
         //保存用户买入
         model.setId(UUID.randomUUID().toString());
         model.setSellId(ums.getId());
         model.setTypeId(ums.getTypeId());
//         model.setUserId(user.getId());
         model.setCreateTime(new Date());
         userMoneyBuyService.submit(model);
         
         //卖单剩余金额减少
         ums.setLeaveMoney(ums.getLeaveMoney() - model.getMoney());
         if(ums.getLeaveMoney() == 0){
             ums.setStatus(SellStatus.SELL_OVER.getValue());
         }
         userMoneySellService.submit(ums);
         
         //用户卖出
         userMoneyService.save(ums.getUserId(), ums.getTypeId(), -model.getMoney(), MoneyLogType.SELL, null);//卖出
         userMoneyService.save(ums.getUserId(), ums.getBuyTypeId(), model.getMoney(), MoneyLogType.BUY, null);//买入
         
         //用户买入
//         userMoneyService.save(user.getId(), ums.getTypeId(), model.getMoney(), MoneyLogType.BUY, null);//卖出
//         userMoneyService.save(user.getId(), ums.getBuyTypeId(), -model.getMoney(), MoneyLogType.SELL, null);//买入
         
         resp.setRows(model);
         resp.setTotal(1);
         return resp;
     }
}
