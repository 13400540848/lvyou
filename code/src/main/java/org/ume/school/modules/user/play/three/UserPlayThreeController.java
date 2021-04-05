package org.ume.school.modules.user.play.three;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ume.school.modules.model.ErrorTag;
import org.ume.school.modules.model.entity.MoneyType;
import org.ume.school.modules.model.entity.PlayThreeResult;
import org.ume.school.modules.model.entity.UserMoney;
import org.ume.school.modules.model.entity.UserPlayThree;
import org.ume.school.modules.model.entity.UserPlayThreeInfo;
import org.ume.school.modules.model.enums.MoneyLogType;
import org.ume.school.modules.model.enums.UserPlayStatus;
import org.ume.school.modules.model.enums.UserPlayThreeType;
import org.ume.school.modules.moneytype.MoneyTypeService;
import org.ume.school.modules.play.three.result.PlayThreeResultService;
import org.ume.school.modules.request.UserPlayThreeRequest;
import org.ume.school.modules.user.money.UserMoneyService;
import org.ume.school.modules.user.play.three.info.UserPlayThreeInfoService;

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
 * Created by Zz on 2018/11/1.
 */
@RestController
@RequestMapping("/v0.1/user/play/three")
public class UserPlayThreeController {

    @Resource
    private UserPlayThreeService userPlayThreeService;
    @Resource
    private UserPlayThreeInfoService userPlayThreeInfoService;    
        
    @Resource
    private PlayThreeResultService playThreeResultService; 
    
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
    public Response<List<UserPlayThree>> getList(@RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "offset", required = true) String offset,
            @RequestParam(value = "limit", required = true) String limit, @Logined IUser user) {
        Response<List<UserPlayThree>> check = AuthValidate.checkUser(user);
        if (check != null) {
            return check;
        }
        Response<List<UserPlayThree>> result = new Response<List<UserPlayThree>>();
        final UserPlayThree query = new UserPlayThree();
//        query.setUserId(user.getId());
        query.setStatus(status);
        Page<UserPlayThree> projects = userPlayThreeService.findAll(query, offset, limit);
        result.setTotal(projects.getTotalElements());
//        result.setRows(userPlayThreeService.parse(projects.getContent()));
        result.setRows(projects.getContent());
        return result;
    }
    
    /**
     * 获取中奖列表
     */
    @RequestMapping(value = "/reward/list", method = RequestMethod.GET)
    public Response<List<UserPlayThree>> getRewardList(@RequestParam(value = "offset", required = true) String offset,
            @RequestParam(value = "limit", required = true) String limit) {
        Response<List<UserPlayThree>> result = new Response<List<UserPlayThree>>();
        final UserPlayThree query = new UserPlayThree();
        query.setStatus(UserPlayStatus.YES.getValue());
        Page<UserPlayThree> projects = userPlayThreeService.findAll(query, offset, limit);
        result.setTotal(projects.getTotalElements());
        result.setRows(userPlayThreeService.parse(projects.getContent()));
        result.setRows(projects.getContent());
        return result;
    }
    
    /**
     * 获取订单
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<UserPlayThree> getInfo(@PathVariable("id") String id, @Logined IUser user) {
        Response<UserPlayThree> check = AuthValidate.checkUser(user);
        if (check != null) {
            return check;
        }
        Response<UserPlayThree> resp = new Response<UserPlayThree>();
        UserPlayThree model = userPlayThreeService.get(id);
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
     public Response<UserPlayThree> submit(@RequestBody UserPlayThreeRequest req, @Logined IUser user, HttpServletRequest request) {
         Response<UserPlayThree> check = AuthValidate.checkUser(user);
         if (check != null) {
             return check;
         }
         Response<UserPlayThree> resp = new Response<>();       
         try{
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
//	         if (!u.getDealPassword().equals(MD5Util.getMD5(req.getDealPassword()))) {
//	             resp.setResultCode(ErrorTag.USER_DEAL_PASSWORD_ERROR.getCode());
//	             resp.setResultMsg(ErrorTag.USER_DEAL_PASSWORD_ERROR.getMessage());
//	             return resp;
//	         }
	         //系统币种
	         MoneyType mtSystem = moneyTypeService.findBySystem();
	         if(mtSystem==null){
	             resp.setResultMsg(ErrorTag.MONEY_TYPE_NOT_EXISTS.getMessage());
	             resp.setResultCode(ErrorTag.MONEY_TYPE_NOT_EXISTS.getCode());
	             return resp;
	         }
	         //获取投注期
	         PlayThreeResult waitResult = playThreeResultService.getWaitResult();
	         if(waitResult==null || waitResult.getEndTime().before(new Date())){
	             resp.setResultCode(ErrorTag.USER_PLAY_RESULT_TIME_OVER.getCode());
	             resp.setResultMsg(ErrorTag.USER_PLAY_RESULT_TIME_OVER.getMessage());
	             return resp;
	         }
	         //验证逻辑
	         List<UserPlayThree> listUps = new ArrayList<UserPlayThree>();
	         List<UserPlayThreeInfo> listUpInfos = new ArrayList<UserPlayThreeInfo>();
	         Double allMoney = (double)0;
	         for(UserPlayThree userPlay : req.getData()){
	             if(userPlay.getMode()==null || userPlay.getNumber().isEmpty()){
	                 resp.setResultCode(ErrorTag.USER_PLAY_RESULT_ERROR.getCode());
	                 resp.setResultMsg(ErrorTag.USER_PLAY_RESULT_ERROR.getMessage());
	                 return resp;
	             }
	             String orderId = RandomUtil.createOrderId();
	             //和值
	             if(userPlay.getMode().intValue() == UserPlayThreeType.SUM.getValue()) {	            	 
	            	 String[] arrHz = userPlay.getNumber().split(",");
	            	 for(String itemHz : arrHz) {
	            		 int hz = Integer.parseInt(itemHz);
	            		 if(hz<3 || hz>18) {
	                		 resp.setResultCode(ErrorTag.USER_PLAY_RESULT_ERROR.getCode());
	                         resp.setResultMsg(ErrorTag.USER_PLAY_RESULT_ERROR.getMessage());
	                         return resp;
	                	 }
	            		 UserPlayThreeInfo upti = getUserPlayInfo(orderId, userPlay.getMode().intValue(), itemHz, waitResult, u);
	            		 listUpInfos.add(upti);
	    	             allMoney+=waitResult.getPerMoney();
	            	 }
    	             userPlay = getUserPlay(userPlay, orderId, arrHz.length, waitResult.getPerMoney(), waitResult, u);
    	             listUps.add(userPlay);
    	             continue;
	             }
	             //一个号通选
	             if(userPlay.getMode().intValue() == UserPlayThreeType.ONE_ALL.getValue()) {	                 
	                 int i = Integer.parseInt(userPlay.getNumber());
                     if(i <=0 || i > 6){
                         resp.setResultCode(ErrorTag.USER_PLAY_RESULT_ERROR.getCode());
                         resp.setResultMsg(ErrorTag.USER_PLAY_RESULT_ERROR.getMessage());
                         return resp;
                     }
	                 UserPlayThreeInfo upti = getUserPlayInfo(orderId, userPlay.getMode().intValue(), userPlay.getNumber(), waitResult, u);
                     listUpInfos.add(upti);
                     userPlay = getUserPlay(userPlay, orderId, 21, waitResult.getPerMoney(), waitResult, u);
                     listUps.add(userPlay);
                     continue;
	             }
	             //三同号通选
	             if(userPlay.getMode().intValue() == UserPlayThreeType.THREE_SAME_ALL.getValue()) {
	            	 UserPlayThreeInfo upti = getUserPlayInfo(orderId, userPlay.getMode().intValue(), "三同号通选", waitResult, u);
            		 listUpInfos.add(upti);
    	             userPlay = getUserPlay(userPlay, orderId, 1, waitResult.getPerMoney(), waitResult, u);
    	             listUps.add(userPlay);
    	             continue;
	             }
	             List<String> arrSysThreeSame = Arrays.asList(new String[]{"111", "222", "333", "444", "555", "666"});
	             //三同号单选
                 if(userPlay.getMode().intValue() == UserPlayThreeType.THREE_SAME_SINGLE.getValue()) {
                     String number = userPlay.getNumber().replace(" ", "").replace(",", "");
                     if(!arrSysThreeSame.contains(number)){
                         resp.setResultCode(ErrorTag.USER_PLAY_RESULT_ERROR.getCode());
                         resp.setResultMsg(ErrorTag.USER_PLAY_RESULT_ERROR.getMessage());
                         return resp;
                     }
                     UserPlayThreeInfo upti = getUserPlayInfo(orderId, userPlay.getMode().intValue(), number, waitResult, u);
                     listUpInfos.add(upti);
                     userPlay = getUserPlay(userPlay, orderId, 1, waitResult.getPerMoney(), waitResult, u);
                     listUps.add(userPlay);
                     continue;
                 }
                 //三不同号
                 if(userPlay.getMode().intValue() == UserPlayThreeType.THREE_SAME_SINGLE.getValue()) {
                     String[] arrNumbers = userPlay.getNumber().split(" ");
                     if(arrNumbers==null || arrNumbers.length <= 2){
                         resp.setResultCode(ErrorTag.USER_PLAY_RESULT_ERROR.getCode());
                         resp.setResultMsg(ErrorTag.USER_PLAY_RESULT_ERROR.getMessage());
                         return resp;
                     }
                     List<String> arrNumber = new ArrayList<String>();
                     for(String s : arrNumbers){
                         int i = Integer.parseInt(s);
                         if(i <=0 || i > 6 || arrNumber.contains(s)){
                             resp.setResultCode(ErrorTag.USER_PLAY_RESULT_ERROR.getCode());
                             resp.setResultMsg(ErrorTag.USER_PLAY_RESULT_ERROR.getMessage());
                             return resp;
                         }
                         arrNumber.add(s);
                     }
                     List<String[]> combinationList = new ArrayList<String[]>();
                     getCombinationList(arrNumbers, 0, new String[3], 0, combinationList);
                     for(String[] s : combinationList){
                         UserPlayThreeInfo upti = getUserPlayInfo(orderId, userPlay.getMode().intValue(), StringUtils.join(s, ""), waitResult, u);
                         listUpInfos.add(upti);
                     }
                     userPlay = getUserPlay(userPlay, orderId, combinationList.size(), waitResult.getPerMoney(), waitResult, u);
                     listUps.add(userPlay);
                     continue;
                 }
                 //三连号通选
                 if(userPlay.getMode().intValue() == UserPlayThreeType.THREE_LINK_ALL.getValue()) {
                     UserPlayThreeInfo upti = getUserPlayInfo(orderId, userPlay.getMode().intValue(), "三连号通选", waitResult, u);
                     listUpInfos.add(upti);
                     userPlay = getUserPlay(userPlay, orderId, 1, waitResult.getPerMoney(), waitResult, u);
                     listUps.add(userPlay);
                     continue;
                 }
                 List<String> arrSysTwoSame = Arrays.asList(new String[]{"11*", "22*", "33*", "44*", "55*", "66*"});
                 //二同号复选
                 if(userPlay.getMode().intValue() == UserPlayThreeType.TWO_SAME_ALL.getValue()) {
                     String[] arrNumbers = userPlay.getNumber().split(",");                     
                     if(arrNumbers==null || arrNumbers.length <= 0){
                         resp.setResultCode(ErrorTag.USER_PLAY_RESULT_ERROR.getCode());
                         resp.setResultMsg(ErrorTag.USER_PLAY_RESULT_ERROR.getMessage());
                         return resp;
                     }
                     for(String s : arrNumbers){
                         if(!arrSysTwoSame.contains(s)){
                             resp.setResultCode(ErrorTag.USER_PLAY_RESULT_ERROR.getCode());
                             resp.setResultMsg(ErrorTag.USER_PLAY_RESULT_ERROR.getMessage());
                             return resp;
                         }
                         UserPlayThreeInfo upti = getUserPlayInfo(orderId, userPlay.getMode().intValue(), s, waitResult, u);
                         listUpInfos.add(upti);
                     }
                     userPlay = getUserPlay(userPlay, orderId, arrNumbers.length, waitResult.getPerMoney(), waitResult, u);
                     listUps.add(userPlay);
                     continue;
                 }
                 List<String> arrSysTwoSameNo = Arrays.asList(new String[]{"11", "22", "33", "44", "55", "66"});
                 //二同号单选
                 if(userPlay.getMode().intValue() == UserPlayThreeType.TWO_SAME_SINGLE.getValue()) {
                     String[] arrNumbers = userPlay.getNumber().split("#");                     
                     if(arrNumbers==null || arrNumbers.length != 2){
                         resp.setResultCode(ErrorTag.USER_PLAY_RESULT_ERROR.getCode());
                         resp.setResultMsg(ErrorTag.USER_PLAY_RESULT_ERROR.getMessage());
                         return resp;
                     }
                     String[] arr1 = arrNumbers[0].split(" ");
                     String[] arr2 = arrNumbers[1].split(" ");
                     for(String s1 : arr1){
                         if(!arrSysTwoSameNo.contains(s1)){
                             resp.setResultCode(ErrorTag.USER_PLAY_RESULT_ERROR.getCode());
                             resp.setResultMsg(ErrorTag.USER_PLAY_RESULT_ERROR.getMessage());
                             return resp;
                         }
                         for(String s2 : arr2){
                             if(s1.contains(s2)){
                                 resp.setResultCode(ErrorTag.USER_PLAY_RESULT_ERROR.getCode());
                                 resp.setResultMsg(ErrorTag.USER_PLAY_RESULT_ERROR.getMessage());
                                 return resp;
                             }
                             UserPlayThreeInfo upti = getUserPlayInfo(orderId, userPlay.getMode().intValue(), s1+s2, waitResult, u);
                             listUpInfos.add(upti);
                         }
                     }
                     userPlay = getUserPlay(userPlay, orderId, arr1.length*arr2.length, waitResult.getPerMoney(), waitResult, u);
                     listUps.add(userPlay);
                     continue;
                 }
                 //二不同号
                 if(userPlay.getMode().intValue() == UserPlayThreeType.TWO_NO_SAME_ALL.getValue()) {
                     String[] arrNumbers = userPlay.getNumber().split(" ");
                     if(arrNumbers==null || arrNumbers.length <= 1){
                         resp.setResultCode(ErrorTag.USER_PLAY_RESULT_ERROR.getCode());
                         resp.setResultMsg(ErrorTag.USER_PLAY_RESULT_ERROR.getMessage());
                         return resp;
                     }
                     List<String> arrNumber = new ArrayList<String>();
                     for(String s : arrNumbers){
                         int i = Integer.parseInt(s);
                         if(i <=0 || i > 6 || arrNumber.contains(s)){
                             resp.setResultCode(ErrorTag.USER_PLAY_RESULT_ERROR.getCode());
                             resp.setResultMsg(ErrorTag.USER_PLAY_RESULT_ERROR.getMessage());
                             return resp;
                         }
                         arrNumber.add(s);
                     }
                     List<String[]> combinationList = new ArrayList<String[]>();
                     getCombinationList(arrNumbers, 0, new String[2], 0, combinationList);
                     for(String[] s : combinationList){
                         UserPlayThreeInfo upti = getUserPlayInfo(orderId, userPlay.getMode().intValue(), StringUtils.join(s, ""), waitResult, u);
                         listUpInfos.add(upti);
                     }
                     userPlay = getUserPlay(userPlay, orderId, combinationList.size(), waitResult.getPerMoney(), waitResult, u);
                     listUps.add(userPlay);
                     continue;
                 }
	         }
	         
	         allMoney=waitResult.getPerMoney()*listUpInfos.size();
	         //余额不足
//	         UserMoney um = userMoneyService.findByUserIdAndTypeId(user.getId(), mtSystem.getTypeId());
//	         if(um==null ||um.getMoney()<=0 || um.getMoney() < allMoney){
//	             resp.setResultMsg(ErrorTag.USER_PLAY_MONEY_NOT_ENOUTH.getMessage());
//	             resp.setResultCode(ErrorTag.USER_PLAY_MONEY_NOT_ENOUTH.getCode());
//	             return resp;
//	         }
	         //保存用户投注
	         for(UserPlayThree ups : listUps){
	             userPlayThreeService.submit(ups);
	         }
	         for(UserPlayThreeInfo upti : listUpInfos){
	             userPlayThreeInfoService.submit(upti);
	         }
	         //用户钱包扣除
	         //userMoneyService.save(u.getId(), mtSystem.getTypeId(), -allMoney, MoneyLogType.PLAY, null);
	         
	         resp.setTotal(1);
	         return resp;
         }catch(Exception ex) {
        	 resp.setResultCode(ErrorTag.USER_PLAY_RESULT_ERROR.getCode());
             resp.setResultMsg(ErrorTag.USER_PLAY_RESULT_ERROR.getMessage());
             return resp;
         }
     }
     
     private UserPlayThreeInfo getUserPlayInfo(String orderId, int mode, String number, PlayThreeResult waitResult, UserEntity u) {
    	 UserPlayThreeInfo upti = new UserPlayThreeInfo();
    	 upti.setId(UUID.randomUUID().toString());
    	 upti.setMode(mode);
    	 upti.setNumber(number);
		 upti.setRewardMoney((double)0);
		 upti.setRewardCode(0);
		 upti.setCreateTime(new Date());
		 upti.setOrderId(orderId);
		 upti.setPlayTime(waitResult.getPlayTime());
		 upti.setPublishTime(waitResult.getPublishTime());
		 upti.setStatus(UserPlayStatus.WAIT.getValue());
		 upti.setMoney(waitResult.getPerMoney());
		 //upti.setUserId(u.getId());
		 return upti;
     }
     private UserPlayThree getUserPlay(UserPlayThree userPlay, String orderId, Integer countNumber, Double perMoney, PlayThreeResult waitResult, UserEntity u) {
    	 userPlay.setCountNumber(countNumber);
         userPlay.setCountReward((double)0);
         userPlay.setCreateTime(new Date());
         userPlay.setOrderId(orderId);
         userPlay.setPerMoney(waitResult.getPerMoney());
         userPlay.setPlayTime(waitResult.getPlayTime());
         userPlay.setPublishTime(waitResult.getPublishTime());
         userPlay.setStatus(UserPlayStatus.WAIT.getValue());
         userPlay.setSumMoney(perMoney*countNumber);
         //userPlay.setUserId(u.getId());
         return userPlay;
     }
     private void getCombinationList(String[] dataList, int dataIndex, String[] resultList, int resultIndex, List<String[]> combinationList) {  
         int resultLen = resultList.length;
         int resultCount = resultIndex + 1;
         if (resultCount > resultLen) { // 全部选择完时，输出组合结果
             System.out.println(Arrays.asList(resultList));
             combinationList.add(resultList.clone());
             return;
         }

         // 递归选择下一个
         for (int i = dataIndex; i < dataList.length + resultCount - resultLen; i++) {
             resultList[resultIndex] = dataList[i];
             getCombinationList(dataList, i + 1, resultList, resultIndex + 1, combinationList);
         }
     }
}
