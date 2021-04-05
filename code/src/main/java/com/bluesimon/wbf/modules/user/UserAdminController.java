package com.bluesimon.wbf.modules.user;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.ume.school.modules.model.ErrorTag;
import org.ume.school.modules.model.entity.UserInviteReward;
import org.ume.school.modules.model.enums.MoneyLogType;
import org.ume.school.modules.model.enums.UserInviteRewardStatus;
import org.ume.school.modules.user.invite.reward.UserInviteRewardService;
import org.ume.school.modules.user.money.UserMoneyService;

import com.bluesimon.wbf.AdminLogined;
import com.bluesimon.wbf.IUser;
import com.bluesimon.wbf.Logined;
import com.bluesimon.wbf.RequestPager;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.ResponseErrorEnum;
import com.bluesimon.wbf.modules.menu.MenuEntity;
import com.bluesimon.wbf.modules.menu.MenuService;
import com.bluesimon.wbf.modules.role.RoleEntity;
import com.bluesimon.wbf.modules.user.dto.ModifyPasswordDTO;
import com.bluesimon.wbf.modules.user.enums.UserCheckStatusEnum;
import com.bluesimon.wbf.modules.user.enums.UserStatusEnum;
import com.bluesimon.wbf.modules.user.enums.UserTypeEnum;
import com.bluesimon.wbf.utils.AuthValidate;
import com.bluesimon.wbf.utils.MD5Util;
import com.bluesimon.wbf.utils.StringUtil;

/**
 * Created by Zz on 2021/3/20.
 */
@RestController
@RequestMapping("/v0.1/admin/user")
public class UserAdminController {

    @Resource
    private UserService userService;
    @Resource
    private MenuService menuService;
    
    @Resource
    private UserMoneyService userMoneyService;
    @Resource
    private UserInviteRewardService userInviteRewardService;
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Response<UserEntity> login(@RequestBody UserEntity req, HttpServletRequest request, RedirectAttributes attr) {
        if(StringUtil.isEmpty(req.getAccount()) || StringUtil.isEmpty(req.getPassword())){
            return new Response<>(ResponseErrorEnum.PARAM_NULL);
        }
        Response<UserEntity> resp = new Response<>();
        UserEntity u = userService.login(req.getAccount(), MD5Util.getMD5(req.getPassword()));
        if (u == null) {
            return new Response<>("用户名不存在或者密码错误");
        }
        Response<UserEntity> check = AuthValidate.checkAdmin(u);
        if (check != null) {
            return check;
        }
        request.getSession().setAttribute(IUser.LOGIN_ADMIN_USER, u);
        u.setPassword("");
        resp.setRows(u);
        return resp;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public Response<UserEntity> logout(@AdminLogined IUser user, HttpServletRequest request) {
        Response<UserEntity> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        request.getSession().removeAttribute(IUser.LOGIN_ADMIN_USER);
        Response<UserEntity> result = new Response<UserEntity>();
        return result;
    }

    /**
     * 获取当前管理员信息
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Response<UserEntity> info(@AdminLogined IUser user) {
        Response<UserEntity> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        UserEntity u = userService.getUser(user.getId());
        if(u!=null){
            u.setPassword(null);
        }
        return new Response<>(u);
    }

    /**
     * 保存当前用户信息
     */
    @RequestMapping(value = "/saveInfo", method = RequestMethod.POST)
    public Response<UserEntity> saveInfo(@RequestBody UserEntity req, @AdminLogined IUser user) {
        Response<UserEntity> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        return userService.saveInfo(req, user.getId());
    }
    
    /**
     * 用户查询
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Response<List<UserEntity>> page(@RequestBody RequestPager<UserEntity> req, @AdminLogined IUser user) {
        Response<List<UserEntity>> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        Response<List<UserEntity>> result = new Response<List<UserEntity>>();
        Page<UserEntity> list = userService.findAll(req);
        result.setTotal(list.getTotalElements());
        result.setRows(list.getContent());
        return result;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<UserEntity> get(@PathVariable("id") Long id, @AdminLogined IUser user) {
        Response<UserEntity> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        Response<UserEntity> resp = new Response<>();
        UserEntity u = userService.getUser(id);
        if (u == null) {
            return new Response<>("找不到用户");
        }
        resp.setRows(u);
        return resp;
    }
    
    /**
     * 保存
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Response<UserEntity> add(@RequestBody UserEntity req, @AdminLogined IUser user) {
        Response<UserEntity> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        if(StringUtil.isEmpty(req.getAccount()) || StringUtil.isEmpty(req.getPassword())){
            return new Response<>(ResponseErrorEnum.PARAM_NULL);
        }
        if (StringUtil.isEmpty(req.getId())) { //新增
            return userService.add(req);
        }else{
            return userService.edit(req);
        }
    }
    
    /**
     * 重置密码
     */
    @RequestMapping(value = "/password/reset/{id}", method = RequestMethod.POST)
    public Response<UserEntity> resetPassword(@PathVariable("id") Long id, @AdminLogined IUser user) {
        Response<UserEntity> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        UserEntity userDb = userService.getUser(id);
        if(userDb==null){
            return new Response<>("用户不存在");
        }
        userDb.setPassword(MD5Util.getMD5("123456"));
        userDb.setUpdateTime(new Date());
        userDb = userService.update(userDb);
        userDb.setPassword(null);
        return new Response<>(userDb);
    }

    

//    /**
//     * 实名认证
//     */
//    @RequestMapping(value = "/user/check", method = RequestMethod.POST)
//    public Response<User> checkUser(@RequestBody User model, @AdminLogined IUser user) {
//        Response<User> check = AuthValidate.checkAdmin(user);
//        if (check != null) {
//            return check;
//        }
//        Response<User> resp = new Response<>();
//        if (StringUtils.isEmpty(model.getId())) {
//            resp.setResultCode(ErrorTag.PARAM_NULL.getCode());
//            resp.setResultMsg(ErrorTag.PARAM_NULL.getMessage());
//        }
//        else {
//            User modelDb = userService.getUser(model.getId());
//            if (modelDb == null) {
//                resp.setResultCode(ErrorTag.NOT_FIND_USER.getCode());
//                resp.setResultMsg(ErrorTag.NOT_FIND_USER.getMessage());
//            }
//            else {
//                modelDb.setCheckStatus(model.getCheckStatus());
//                modelDb.setCheckReason(model.getCheckReason());
//                resp.setRows(userService.update(modelDb));
//                
//                //认证通过
//                if(model.getCheckStatus().intValue() == UserCheckStatusEnum.NORMAL.getValue()){
//                    //奖励推荐人DG币
//                    User u = userService.getUser(modelDb.getId());
//                    if(u.getReferrerId()!=null && !u.getReferrerId().isEmpty()){
//                        UserInviteReward uir = userInviteRewardService.findByUserIdAndReferrerId(u.getId(), u.getReferrerId());
//                        if(uir!=null && uir.getMoney()>0){
//                            uir.setStatus(UserInviteRewardStatus.SUCCESS.getValue());
//                            userInviteRewardService.submit(uir);
//                            //用户奖励5DG
//                            userMoneyService.save(u.getReferrerId(), uir.getTypeId(), uir.getMoney(), MoneyLogType.INVITE_REWARD, null);
//                        }
//                    }                    
//                }
//            }
//        }
//        return resp;
//    }

    /**
     * 修改密码
     */
    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public Response<UserEntity> modifyPassword(@RequestBody ModifyPasswordDTO model, @AdminLogined IUser user) {
        Response<UserEntity> check = AuthValidate.checkAdmin(user);
        if (check != null) {
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
        return resp;
    }
    
    /**
     * 获取当前用户的菜单列表
     */
    @RequestMapping(value = "/menus", method = RequestMethod.GET)
    public Response<List<MenuEntity>> getUserMenus(@AdminLogined IUser user) {
        Response<List<MenuEntity>> check = AuthValidate.checkAdmin(user);
        if(check!=null){
            return check;
        }       
        List<MenuEntity>list = menuService.getByUserId(user.getId(), user.getType());
        return new Response<>(list);
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Response<UserEntity> delete(@PathVariable("id") Long id, @AdminLogined IUser user) {
        Response<UserEntity> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        return userService.delete(id);
    }

    /**
     * 初始化账号
     *
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public Response<UserEntity> initAdmin() {
        int count = userService.countByAccount("admin");
        if (count <= 0) {
            UserEntity user = new UserEntity();
            user.setNickName("超级管理员");
            user.setRealName("超级管理员");
            user.setAccount("admin");
            // 两次MD5，一次是传输前，一次是传输后
            user.setPassword("123456"); //E10ADC3949BA59ABBE56E057F20F883E
            user.setType(UserTypeEnum.ADMIN.getValue());
            user.setStatus(UserStatusEnum.NORMAL.getValue());
            userService.add(user);
            return new Response<>();
        }
        return new Response<>();
    }
}
