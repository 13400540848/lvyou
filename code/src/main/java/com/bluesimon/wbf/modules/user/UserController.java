package com.bluesimon.wbf.modules.user;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.ume.school.modules.config.ConfigService;
import org.ume.school.modules.model.ErrorTag;
import org.ume.school.modules.moneytype.MoneyTypeService;
import org.ume.school.modules.user.invite.reward.UserInviteRewardService;
import org.ume.school.modules.user.money.UserMoneyService;

import com.bluesimon.wbf.IUser;
import com.bluesimon.wbf.Logined;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.ResponseErrorEnum;
import com.bluesimon.wbf.modules.user.dto.ModifyPasswordDTO;
import com.bluesimon.wbf.modules.user.enums.UserStatusEnum;
import com.bluesimon.wbf.modules.user.enums.UserTypeEnum;
import com.bluesimon.wbf.utils.AuthValidate;
import com.bluesimon.wbf.utils.MD5Util;
import com.bluesimon.wbf.utils.RandomUtil;
import com.bluesimon.wbf.utils.SMSUtil;
import com.bluesimon.wbf.utils.StringUtil;
import com.bluesimon.wbf.view.VerificationCodeController;

/**
 * Created by Zz on 2018/8/27.
 */
@RestController
@RequestMapping("/v0.1/user")
public class UserController {

    @Resource
    private UserService userService;
    
    @Resource
    private ConfigService configService;
    
    @Resource
    private UserMoneyService userMoneyService;
    
    @Resource
    private MoneyTypeService moneyTypeService;
    
    @Resource
    private UserInviteRewardService userInviteRewardService;
    
    /**
     * 快速注册
     *
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Response<UserEntity> register(@RequestBody UserEntity req,                                   
                                   HttpServletRequest request,
                                   RedirectAttributes attr) {
        if(StringUtil.isEmpty(req.getAccount()) || StringUtil.isEmpty(req.getPassword()) || StringUtil.isEmpty(req.getValidateCode())){
            return new Response<>(ResponseErrorEnum.PARAM_NULL);
        }
        Response<UserEntity> resp = new Response<>();
        //验证码
//        String resultCode = (String) request.getSession().getAttribute(VerificationCodeController.SESSION_KEY_OF_RAND_CODE);
//        if(!resultCode.toLowerCase().equals(validatecode.toLowerCase())){
//            resp.setResultCode(ErrorTag.VALEDATE_CODE_ERROR.getCode());
//            resp.setResultMsg(ErrorTag.VALEDATE_CODE_ERROR.getMessage());
//            return resp;
//        }
        //短信验证码
        Object smsCode = request.getSession().getAttribute(IUser.SMS_CODE);
        if (smsCode == null || smsCode.toString() == null) {
            resp.setResultCode(ErrorTag.USER_SMS_CODE_NULL.getCode());
            resp.setResultMsg(ErrorTag.USER_SMS_CODE_NULL.getMessage());
            return resp;
        }
        if(!req.getValidateCode().equals(smsCode.toString())){
            resp.setResultCode(ErrorTag.USER_SMS_CODE_ERROR.getCode());
            resp.setResultMsg(ErrorTag.USER_SMS_CODE_ERROR.getMessage());
            return resp;
        }
        //用户已存在
        int count = userService.countByAccount(req.getAccount());
        if(count > 0){
            request.getSession().removeAttribute(IUser.SMS_CODE);
            resp.setResultCode(ErrorTag.REPEAT_USER_NAME.getCode());
            resp.setResultMsg(ErrorTag.REPEAT_USER_NAME.getMessage());
            return resp;
        }
        //推荐人
        UserEntity user = new UserEntity();
//        String url = "";
//        User ru = null;
//        if(referrerid!=null &&referrerid>0){
//            ru = userService.getUserByBizId(referrerid);
//            if(ru==null){
//                resp.setResultCode(ErrorTag.REFERRE_USER_NOT_EXISTS.getCode());
//                resp.setResultMsg(ErrorTag.REFERRE_USER_NOT_EXISTS.getMessage());
//                return resp;
//            }           
//            url = "/"+referrerid+"/";
//            if(ru.getReferreUrl()!=null &&!ru.getReferreUrl().isEmpty()){
//                url=ru.getReferreUrl()+referrerid+"/";
//            }
//            user.setReferrerId(ru.getId());
//            user.setReferreUrl(url);
//        }
        user.setAccount(req.getAccount());
        user.setMobilePhone(req.getAccount());
        user.setPassword(req.getPassword());
//        user.setDealPassword(MD5Util.getMD5(dealpassword));
        user.setNickName(req.getAccount());
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setType(UserTypeEnum.USER.getValue());
        user.setStatus(UserStatusEnum.NORMAL.getValue());
//        user.setCheckStatus(UserCheckStatusEnum.UN_SUBMIT.getValue());
        resp = userService.add(user);
        request.getSession().setAttribute(IUser.LOGIN_USER, resp.getRows());
       
//        if(ru!=null){
//            //推荐人送DG，等待实名认证
//            MoneyType mt = moneyTypeService.findBySystem();
//            if(mt!=null){
//               double dg = configService.getInviteRewardDG();
//               if(dg>0){
//                   UserInviteReward uir = new UserInviteReward();
//                   uir.setId(UUID.randomUUID().toString());
//                   uir.setCreateTime(new Date());
//                   uir.setMoney(dg);
//                   uir.setReferrerId(ru.getId());
//                   uir.setStatus(UserInviteRewardStatus.WAIT_CHECK.getValue());
//                   uir.setTypeId(mt.getTypeId());
//                   uir.setUserId(u.getId());
//                   userInviteRewardService.submit(uir);
//               }
//            }
//        }
        
        //代币和DG币钱包为0
//        List<MoneyType> listMT = moneyTypeService.findList();
//        if(listMT!=null && !listMT.isEmpty()){
//            for(MoneyType mt : listMT){
//                if(mt.getTypeMode().intValue()!=MoneyTypeMode.INVEST.getValue()){
//                    //用户钱包为0
//                    userMoneyService.findByUserIdAndTypeId(u.getId(), mt.getTypeId());
//                }
//            }
//        }
        return resp;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Response<UserEntity> login(@RequestBody UserEntity req,
                                HttpServletRequest request,
                                RedirectAttributes attr) {
        if(StringUtil.isEmpty(req.getAccount()) || StringUtil.isEmpty(req.getPassword()) || StringUtil.isEmpty(req.getValidateCode())){
            return new Response<>(ResponseErrorEnum.PARAM_NULL);
        }
        Response<UserEntity> resp = new Response<>();
        String resultCode = (String) request.getSession().getAttribute(VerificationCodeController.SESSION_KEY_OF_RAND_CODE);
        if(!resultCode.toLowerCase().equals(req.getValidateCode().toLowerCase())){
            return new Response<>("验证码错误");
        }
        UserEntity u = userService.login(req.getAccount(), MD5Util.getMD5(req.getPassword()));
        if (u == null) {
            return new Response<>("用户名不存在或者密码错误");
        } 
        if(u.getType() == UserTypeEnum.ADMIN.getValue()) {
            return new Response<>("当前用户是管理员");
        }
        request.getSession().setAttribute(IUser.LOGIN_USER, u);
        return resp;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public Response<UserEntity> logout(@Logined IUser user, HttpServletRequest request) {
        Response<UserEntity> check = AuthValidate.checkUser(user);
        if(check!=null){
            return check;
        }
        request.getSession().removeAttribute(IUser.LOGIN_USER);
        Response<UserEntity> result = new Response<UserEntity>();
        return result;
    }
    
    /**
     * 获取当前用户信息
     * @param user
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Response<UserEntity> getUserInfo(@Logined IUser user) {
        Response<UserEntity> check = AuthValidate.checkUser(user);
        if(check!=null){
            return check;
        }
        UserEntity u = userService.getUser(user.getId());
        u.setPassword("");
//        u.setDealPassword("");
        return new Response<>(u);
    }
    
    /**
     * 修改基本信息
     */
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public Response<UserEntity> modifyUserInfo(@RequestBody UserEntity userReq,
            @Logined IUser user,
            HttpServletRequest request) {
        Response<UserEntity> check = AuthValidate.checkUser(user);
        if(check!=null){
            return check;
        }        
        Response<UserEntity> resp = new Response<>();
        if (StringUtil.isEmpty(userReq.getHeadImage()) || StringUtil.isEmpty(userReq.getNickName()) 
                || StringUtil.isEmpty(userReq.getMail()) || StringUtil.isEmpty(userReq.getSex())) {
            return new Response<>(ResponseErrorEnum.PARAM_NULL);
        }
        UserEntity u = userService.getUser(user.getId());
        u.setNickName(userReq.getNickName());
        u.setMail(userReq.getMail());
        u.setHeadImage(userReq.getHeadImage());
        u.setSex(userReq.getSex().intValue());
//        u.setAddr(userReq.getAddr());
//        u.setCity(userReq.getCity());
        u.setUpdateTime(new Date());
        u = userService.update(u);
        resp.setRows(u);
        return resp;
    }
    
    /**
     * 发送手机验证码
     */
    @RequestMapping(value = "/sms", method = RequestMethod.POST)
    public Response<UserEntity> smsUserMobileCode(@Logined IUser user,
            HttpServletRequest request) {
        Response<UserEntity> check = AuthValidate.checkUser(user);
        if(check!=null){
            return check;
        }
        Response<UserEntity> resp = new Response<>();
        UserEntity u = userService.getUser(user.getId());
        
        //生成6位数字验证码
        String code = RandomUtil.createNumber(6);
        //发送验证码到手机
        SMSUtil sms = new SMSUtil();
        try {
            sms.send(u.getMobilePhone(), code);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //设置session
        request.getSession().setAttribute(IUser.SMS_CODE, code);
        return resp;
    }
    
    /**
     * 绑定手机
     */
    @RequestMapping(value = "/mobile", method = RequestMethod.POST)
    public Response<UserEntity> modifyUserMobile(@RequestBody UserEntity userReq,
            @Logined IUser user,
            HttpServletRequest request) {
        Response<UserEntity> check = AuthValidate.checkUser(user);
        if(check!=null){
            return check;
        }        
        Response<UserEntity> resp = new Response<>();
        if (userReq.getMobilePhone() == null || userReq.getSmsCode() == null) {
            return new Response<>(ResponseErrorEnum.PARAM_NULL);
        }
        Object smsCode = request.getSession().getAttribute(IUser.SMS_CODE);
        if (smsCode == null || smsCode.toString() == null) {
            return new Response<>("请先点击获取手机短信验证码");
        }
        if(!userReq.getSmsCode().equals(smsCode.toString())){
            return new Response<>("手机短信验证码错误");
        }        
        UserEntity u = userService.getUser(user.getId());
        u.setMobilePhone(userReq.getMobilePhone());
        u.setUpdateTime(new Date());
        u = userService.update(u);
        resp.setRows(u);
        
        request.getSession().removeAttribute(IUser.SMS_CODE);
        
        return resp;
    }

//    /**
//     * 身份认证
//     */
//    @RequestMapping(value = "/check", method = RequestMethod.POST)
//    public Response<User> modifyCheckInfo(@RequestBody User userReq,
//            @Logined IUser user,
//            HttpServletRequest request) {
//        Response<User> check = AuthValidate.checkUser(user);
//        if(check!=null){
//            return check;
//        }        
//        Response<User> resp = new Response<>();
//        //参数错误
//        if (userReq.getRealName().isEmpty() || userReq.getCardId().isEmpty() 
//                || userReq.getCardImage1().isEmpty() || userReq.getCardImage2().isEmpty() || userReq.getCardImage3().isEmpty()) {
//            resp.setResultCode(ErrorTag.PARAM_NULL.getCode());
//            resp.setResultMsg(ErrorTag.PARAM_NULL.getMessage());
//            return resp;
//        }
//        //验证码
//        String resultCode = (String) request.getSession().getAttribute(VerificationCodeController.SESSION_KEY_OF_RAND_CODE);
//        if(!resultCode.toLowerCase().equals(userReq.getValidateCode().toLowerCase())){
//            resp.setResultCode(ErrorTag.VALEDATE_CODE_ERROR.getCode());
//            resp.setResultMsg(ErrorTag.VALEDATE_CODE_ERROR.getMessage());
//            return resp;
//        }
//        //认证状态
//        User u = userService.getUser(user.getId());
//        if(u.getCheckStatus().intValue() == UserCheckStatusEnum.NORMAL.getValue()){
//            resp.setResultCode(ErrorTag.USER_CHECKED.getCode());
//            resp.setResultMsg(ErrorTag.USER_CHECKED.getMessage());
//            return resp;
//        }
//        u.setRealName(userReq.getRealName());
//        u.setCardId(userReq.getCardId());
//        u.setCardImage1(userReq.getCardImage1());
//        u.setCardImage2(userReq.getCardImage2());
//        u.setCardImage3(userReq.getCardImage3());
//        u.setCheckStatus(UserCheckStatusEnum.UN_CHECK.getValue());
//        u.setLastModified(new Date());
//        u = userService.update(u);
//        resp.setRows(u);
//        return resp;
//    }

    /**
     * 修改密码
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/pw", method = RequestMethod.POST)
    public Response<UserEntity> modifyPassword(@RequestBody ModifyPasswordDTO model,
            @Logined IUser user, HttpServletRequest request) {
        Response<UserEntity> check = AuthValidate.checkUser(user);
        if(check!=null){
            return check;
        }
        Response<UserEntity> resp = new Response<>();
        UserEntity u = userService.login(user.getAccount(), MD5Util.getMD5(model.getOldPassword()));
        if (u == null) {
            return new Response<>("原密码错误");
        }
        UserEntity userDb = userService.getUser(user.getId());
        userDb.setPassword(MD5Util.getMD5(model.getNewPassword()));
        userDb = userService.update(userDb);
        resp.setRows(userDb);
        request.getSession().removeAttribute(IUser.LOGIN_USER);
        return resp;
    }
    
//    /**
//     * 修改交易密码
//     *
//     * @param id
//     * @return
//     */
//    @RequestMapping(value = "/deal_pw", method = RequestMethod.POST)
//    public Response<User> modifyDealPassword(@RequestBody UserModifyPassword model,
//            @Logined IUser user, HttpServletRequest request) {
//        Response<User> check = AuthValidate.checkUser(user);
//        if(check!=null){
//            return check;
//        }
//        Response<User> resp = new Response<>();
//        User u = userService.getUser(user.getId());
//        if (!u.getDealPassword().equals(MD5Util.getMD5(model.getOldPassword()))) {
//            resp.setResultCode(ErrorTag.OLD_PASSWORD_ERROR.getCode());
//            resp.setResultMsg(ErrorTag.OLD_PASSWORD_ERROR.getMessage());
//            return resp;
//        }
//        User userDb = userService.getUser(user.getId());
//        userDb.setDealPassword(MD5Util.getMD5(model.getNewPassword()));
//        userDb = userService.update(userDb);
//        resp.setRows(userDb);
//        return resp;
//    }


    /**
     * 验证是否登录, 可作为心跳请求。
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/valid_login", method = RequestMethod.GET)
    public Response<IUser> vaild(@Logined IUser user) {
        if (null != user) {
            return new Response<>(user);
        } else {
            return new Response<>();
        }
    }
}
