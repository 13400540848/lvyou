package org.ume.school.modules.user.play.seven;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ume.school.modules.model.ErrorTag;
import org.ume.school.modules.model.entity.MoneyType;
import org.ume.school.modules.model.entity.PlaySevenResult;
import org.ume.school.modules.model.entity.UserMoney;
import org.ume.school.modules.model.entity.UserPlaySeven;
import org.ume.school.modules.model.entity.UserPlaySeven;
import org.ume.school.modules.model.enums.MoneyLogType;
import org.ume.school.modules.model.enums.PlaySevenMode;
import org.ume.school.modules.model.enums.UserPlayStatus;
import org.ume.school.modules.moneytype.MoneyTypeService;
import org.ume.school.modules.play.seven.result.PlaySevenResultService;
import org.ume.school.modules.request.UserPlaySevenRequest;
import org.ume.school.modules.user.money.UserMoneyService;

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
 * Created by Zz on 2018/10/27.
 */
@RestController
@RequestMapping("/v0.1/user/play/seven")
public class UserPlaySevenController {

    @Resource
    private UserPlaySevenService userPlaySevenService;
        
    @Resource
    private PlaySevenResultService playSevenResultService; 
    
    @Resource
    private UserService userService;
    @Resource
    private UserMoneyService userMoneyService;
    @Resource
    private MoneyTypeService moneyTypeService;
    
    /**
     * 获取列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Response<List<UserPlaySeven>> getList(@RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "offset", required = true) String offset,
            @RequestParam(value = "limit", required = true) String limit, @Logined IUser user) {
        Response<List<UserPlaySeven>> check = AuthValidate.checkUser(user);
        if (check != null) {
            return check;
        }
        Response<List<UserPlaySeven>> result = new Response<List<UserPlaySeven>>();
        final UserPlaySeven query = new UserPlaySeven();
//        query.setUserId(user.getId());
        query.setStatus(status);
        Page<UserPlaySeven> projects = userPlaySevenService.findAll(query, offset, limit);
        result.setTotal(projects.getTotalElements());
//        result.setRows(userPlaySevenService.parse(projects.getContent()));
        result.setRows(projects.getContent());
        return result;
    }
    
    /**
     * 获取中奖列表
     */
    @RequestMapping(value = "/reward/list", method = RequestMethod.GET)
    public Response<List<UserPlaySeven>> getRewardList(@RequestParam(value = "offset", required = true) String offset,
            @RequestParam(value = "limit", required = true) String limit) {
        Response<List<UserPlaySeven>> result = new Response<List<UserPlaySeven>>();
        final UserPlaySeven query = new UserPlaySeven();
        query.setStatus(UserPlayStatus.YES.getValue());
        Page<UserPlaySeven> projects = userPlaySevenService.findAll(query, offset, limit);
        result.setTotal(projects.getTotalElements());
        result.setRows(userPlaySevenService.parse(projects.getContent()));
        result.setRows(projects.getContent());
        return result;
    }
    
    /**
     * 获取订单
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<UserPlaySeven> getInfo(@PathVariable("id") String id, @Logined IUser user) {
        Response<UserPlaySeven> check = AuthValidate.checkUser(user);
        if (check != null) {
            return check;
        }
        Response<UserPlaySeven> resp = new Response<UserPlaySeven>();
        UserPlaySeven model = userPlaySevenService.get(id);
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
     * 我要投注
     */
     @RequestMapping(value = "", method = RequestMethod.POST)
     public Response<UserPlaySeven> submit(@RequestBody UserPlaySevenRequest req, @Logined IUser user, HttpServletRequest request) {
         Response<UserPlaySeven> check = AuthValidate.checkUser(user);
         if (check != null) {
             return check;
         }
         Response<UserPlaySeven> resp = new Response<>();       
         
         //参数错误
         if(req==null || req.getData() == null || req.getData().size()<=0 || req.getDealPassword().isEmpty() || req.getValidateCode().isEmpty()){
             resp.setResultMsg(ErrorTag.PARAM_NULL.getMessage());
             resp.setResultCode(ErrorTag.PARAM_NULL.getCode());
             return resp;
         }
         //验证码错误
         String resultCode = (String) request.getSession().getAttribute(VerificationCodeController.SESSION_KEY_OF_RAND_CODE);
         if(!resultCode.toLowerCase().equals(req.getValidateCode().toLowerCase())){
             resp.setResultCode(ErrorTag.VALEDATE_CODE_ERROR.getCode());
             resp.setResultMsg(ErrorTag.VALEDATE_CODE_ERROR.getMessage());
             return resp;
         }
         //交易密码错误
         UserEntity u = userService.getUser(user.getId());
//         if (!u.getDealPassword().equals(MD5Util.getMD5(req.getDealPassword()))) {
//             resp.setResultCode(ErrorTag.USER_DEAL_PASSWORD_ERROR.getCode());
//             resp.setResultMsg(ErrorTag.USER_DEAL_PASSWORD_ERROR.getMessage());
//             return resp;
//         }
         //系统币种
         MoneyType mtSystem = moneyTypeService.findBySystem();
         if(mtSystem==null){
             resp.setResultMsg(ErrorTag.MONEY_TYPE_NOT_EXISTS.getMessage());
             resp.setResultCode(ErrorTag.MONEY_TYPE_NOT_EXISTS.getCode());
             return resp;
         }
         //获取投注期
         PlaySevenResult waitResult = playSevenResultService.getWaitResult();
         if(waitResult==null){
             resp.setResultCode(ErrorTag.USER_PLAY_RESULT_TIME_OVER.getCode());
             resp.setResultMsg(ErrorTag.USER_PLAY_RESULT_TIME_OVER.getMessage());
             return resp;
         }
         //验证逻辑（暂时不支持复式）
         List<UserPlaySeven> listUps = new ArrayList<UserPlaySeven>();
         Double allMoney = (double)0;
         for(UserPlaySeven userPlay : req.getData()){
             if(userPlay.getSixNumber().isEmpty() || userPlay.getOneNumber().isEmpty()){
                 resp.setResultCode(ErrorTag.USER_PLAY_RESULT_ERROR.getCode());
                 resp.setResultMsg(ErrorTag.USER_PLAY_RESULT_ERROR.getMessage());
                 return resp;
             }
             String[] arrSix = StringUtils.split(userPlay.getSixNumber(), ",");
             if(arrSix.length!=6){
                 resp.setResultCode(ErrorTag.USER_PLAY_RESULT_ERROR.getCode());
                 resp.setResultMsg(ErrorTag.USER_PLAY_RESULT_ERROR.getMessage());
                 return resp;
             }
             for(String s : arrSix){
                 int six = Integer.parseInt(s);
                 if(six <= 0 || six > 33){
                     resp.setResultCode(ErrorTag.USER_PLAY_RESULT_ERROR.getCode());
                     resp.setResultMsg(ErrorTag.USER_PLAY_RESULT_ERROR.getMessage());
                     return resp;
                 }
             }
             int one = Integer.parseInt(userPlay.getOneNumber());
             if(one<=0 || one > 16){
                 resp.setResultCode(ErrorTag.USER_PLAY_RESULT_ERROR.getCode());
                 resp.setResultMsg(ErrorTag.USER_PLAY_RESULT_ERROR.getMessage());
                 return resp;
             }
             userPlay.setCountNumber(1);
             userPlay.setCountReward((double)0);
             userPlay.setCreateTime(new Date());
             userPlay.setMode(PlaySevenMode.SINGLE.getValue());
             userPlay.setOrderId(RandomUtil.createOrderId());
             userPlay.setPerMoney(waitResult.getPerMoney());
             userPlay.setPlayTime(waitResult.getPlayTime());
             userPlay.setPublishTime(waitResult.getPublishTime());
             userPlay.setStatus(UserPlayStatus.WAIT.getValue());
             userPlay.setSumMoney(waitResult.getPerMoney()*userPlay.getCountNumber());
//             userPlay.setUserId(u.getId());
             listUps.add(userPlay);
             allMoney+=userPlay.getSumMoney();
         }
         //余额不足
//         UserMoney um = userMoneyService.findByUserIdAndTypeId(user.getId(), mtSystem.getTypeId());
//         if(um==null ||um.getMoney()<=0 || um.getMoney() < allMoney){
//             resp.setResultMsg(ErrorTag.USER_PLAY_MONEY_NOT_ENOUTH.getMessage());
//             resp.setResultCode(ErrorTag.USER_PLAY_MONEY_NOT_ENOUTH.getCode());
//             return resp;
//         }
         //保存用户投注
         for(UserPlaySeven ups : listUps){
             userPlaySevenService.submit(ups);
         }
         //用户钱包扣除
//         userMoneyService.save(u.getId(), mtSystem.getTypeId(), -allMoney, MoneyLogType.PLAY, null);
         
         resp.setTotal(1);
         return resp;
     }
}
