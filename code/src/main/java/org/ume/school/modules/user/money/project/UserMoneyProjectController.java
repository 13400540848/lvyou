package org.ume.school.modules.user.money.project;

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
import org.ume.school.modules.model.entity.Project;
import org.ume.school.modules.model.entity.ProjectMoney;
import org.ume.school.modules.model.entity.UserMoney;
import org.ume.school.modules.model.entity.UserMoneyProject;
import org.ume.school.modules.model.enums.MoneyLogType;
import org.ume.school.modules.model.enums.ProjectStatus;
import org.ume.school.modules.model.enums.UserMoneyProjectStatus;
import org.ume.school.modules.moneytype.MoneyTypeService;
import org.ume.school.modules.project.ProjectService;
import org.ume.school.modules.project.money.ProjectMoneyService;
import org.ume.school.modules.user.money.UserMoneyService;
import org.ume.school.modules.user.money.log.UserMoneyLogService;

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
@RequestMapping("/v0.1/user/money/project")
public class UserMoneyProjectController {

    @Resource
    private UserMoneyProjectService userMoneyProjectService;
    
    @Resource
    private ProjectService projectService;
    
    @Resource
    private MoneyTypeService moneyTypeService;
    
    @Resource
    private ProjectMoneyService projectMoneyService;
    
    @Resource
    private UserService userService;
    
    @Resource
    private UserMoneyService userMoneyService;
    
    @Resource
    private UserMoneyLogService userMoneyLogService;
    
    /**
     * 获取用户投资项目记录
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Response<List<UserMoneyProject>> getList(@RequestParam(value = "offset", required = true) String offset,
            @RequestParam(value = "limit", required = true) String limit, @Logined IUser user) {
        Response<List<UserMoneyProject>> check = AuthValidate.checkUser(user);
        if (check != null) {
            return check;
        }
        Response<List<UserMoneyProject>> result = new Response<List<UserMoneyProject>>();
        final UserMoneyProject query = new UserMoneyProject();
//        query.setUserId(user.getId());
        Page<UserMoneyProject> projects = userMoneyProjectService.findAll(query, offset, limit);
        result.setTotal(projects.getTotalElements());
        result.setRows(userMoneyProjectService.parse(projects.getContent()));
        return result;
    }
    
    /**
     * 获取列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Response<List<UserMoneyProject>> getList(@Logined IUser user) {
        Response<List<UserMoneyProject>> check = AuthValidate.checkUser(user);
        if (check != null) {
            return check;
        }
//        List<UserMoneyProject> list = userMoneyProjectService.findByUserId(user.getId());
//        return new Response<>(list);
        return null;
    }
    
    /**
     * 投资项目
     * @param project
     * @param user
     * @return
     */
     @RequestMapping(value = "", method = RequestMethod.POST)
     public Response<UserMoneyProject> submit(@RequestBody UserMoneyProject model, @Logined IUser user, HttpServletRequest request) {
         Response<UserMoneyProject> check = AuthValidate.checkUser(user);
         if (check != null) {
             return check;
         }
         Response<UserMoneyProject> resp = new Response<>();
         
         //参数错误
         if(model.getProjectId().isEmpty() || model.getTypeId().isEmpty() || model.getMoney() <= 0 || model.getValidateCode().isEmpty()){
             resp.setResultMsg(ErrorTag.PARAM_NULL.getMessage());
             resp.setResultCode(ErrorTag.PARAM_NULL.getCode());
             return resp;
         }
         //验证码
         String resultCode = (String) request.getSession().getAttribute(VerificationCodeController.SESSION_KEY_OF_RAND_CODE);
         if(!resultCode.toLowerCase().equals(model.getValidateCode().toLowerCase())){
             resp.setResultCode(ErrorTag.VALEDATE_CODE_ERROR.getCode());
             resp.setResultMsg(ErrorTag.VALEDATE_CODE_ERROR.getMessage());
             return resp;
         }
         //项目错误
         Project p = projectService.findById(model.getProjectId());
         if(p==null ||p.getStatus()==null || p.getStatus().intValue()!=ProjectStatus.PUBLISHED.getValue()){
             resp.setResultMsg(ErrorTag.PROJECT_NOT_EXISTS.getMessage());
             resp.setResultCode(ErrorTag.PROJECT_NOT_EXISTS.getCode());
             return resp;
         }
         if(p.getStartTime().after(new Date())){
             resp.setResultMsg(ErrorTag.PROJECT_NOT_START.getMessage());
             resp.setResultCode(ErrorTag.PROJECT_NOT_START.getCode());
             return resp;
         }
         if(p.getEndTime().before(new Date())){
             resp.setResultMsg(ErrorTag.PROJECT_IS_END.getMessage());
             resp.setResultCode(ErrorTag.PROJECT_IS_END.getCode());
             return resp;
         }
         //币种错误
         MoneyType mt = moneyTypeService.findById(model.getTypeId());
         if(mt==null){
             resp.setResultMsg(ErrorTag.MONEY_TYPE_NOT_EXISTS.getMessage());
             resp.setResultCode(ErrorTag.MONEY_TYPE_NOT_EXISTS.getCode());
             return resp;
         }         
         //项目币种不存在
         ProjectMoney pm = projectMoneyService.findByProjectIdAndTypeId(model.getProjectId(), model.getTypeId());
         if(pm==null){
             resp.setResultMsg(ErrorTag.PROJECT_MONEY_NOT_EXISTS.getMessage());
             resp.setResultCode(ErrorTag.PROJECT_MONEY_NOT_EXISTS.getCode());
             return resp;
         }
         //投资金额超过限制
         if(model.getMoney() < pm.getMin() || model.getMoney() > pm.getMax()){
             resp.setResultMsg(ErrorTag.PROJECT_MONEY_NOT_LIMIT.getMessage());
             resp.setResultCode(ErrorTag.PROJECT_MONEY_NOT_LIMIT.getCode());
             return resp;
         }
         //余额不足
//         UserMoney um = userMoneyService.findByUserIdAndTypeId(user.getId(), model.getTypeId());
//         if(um==null ||um.getMoney()<=0 || um.getMoney() < model.getMoney()){
//             resp.setResultMsg(ErrorTag.PROJECT_MONEY_USER_ERROR.getMessage());
//             resp.setResultCode(ErrorTag.PROJECT_MONEY_USER_ERROR.getCode());
//             return resp;
//         }
         //交易密码错误
//         UserEntity u = userService.getUser(user.getId());
//         if (!u.getDealPassword().equals(MD5Util.getMD5(model.getDealPassword()))) {
//             resp.setResultCode(ErrorTag.USER_DEAL_PASSWORD_ERROR.getCode());
//             resp.setResultMsg(ErrorTag.USER_DEAL_PASSWORD_ERROR.getMessage());
//             return resp;
//         }
         
         model.setBrokeMoney(model.getMoney()*p.getBrokePercent()/100);
         model.setRealMoney(model.getMoney()-model.getBrokeMoney());     
         model.setProjectMoney(pm.getMoneyScale()*model.getRealMoney());
         
         //投资金额超过项目剩余金额
         double leaveMoney = pm.getAllMoney() - pm.getMoney();
         if(leaveMoney<model.getRealMoney()){
             resp.setResultMsg(ErrorTag.PROJECT_MONEY_NOT_ENOUTH.getMessage());
             resp.setResultCode(ErrorTag.PROJECT_MONEY_NOT_ENOUTH.getCode());
             return resp;
         }
         //用户已投资过该项目
//         List<UserMoneyProject>umpList = userMoneyProjectService.findByUserIdAndProjectIdAndStatus(user.getId(), model.getProjectId(), BuyProjectStatus.NORMAL.getValue());
//         if(!umpList.isEmpty()){
//             resp.setResultMsg(ErrorTag.PROJECT_MONEY_USER_EXISTS.getMessage());
//             resp.setResultCode(ErrorTag.PROJECT_MONEY_USER_EXISTS.getCode());
//             return resp;
//         }
         
         //保存用户投资项目
         model.setId(UUID.randomUUID().toString());
         model.setOrderId(RandomUtil.createOrderId());
//         model.setUserId(user.getId());
         model.setCreateTime(new Date());
         model.setStatus(UserMoneyProjectStatus.NORMAL.getValue());
         model.setSendMoney((double)0);
         userMoneyProjectService.submit(model);
         
         //保存用户钱包\交易记录
//         userMoneyService.save(user.getId(), model.getTypeId(), -model.getMoney(), MoneyLogType.BUY_PROJECT, null);
         
         //保存项目币种已投资金额
         pm.setMoney(pm.getMoney() + model.getRealMoney());
         projectMoneyService.save(pm);
         
         //保存项目的投资进度和用户总数
         projectService.saveProgress(p.getId());
         p.setUserCount(userMoneyProjectService.countByProjectId(p.getId()));
         projectService.submitProject(p);
         
         resp.setRows(model);
         resp.setTotal(1);
         resp.setResultMsg(model.getOrderId());
         return resp;
     }
     
//     /**
//      * 我要撤销购买项目
//      */
//      @RequestMapping(value = "/cancel", method = RequestMethod.POST)
//      public Response<UserMoneyProject> remove(@RequestBody UserMoneyProject model, @Logined IUser user) {
//          Response<UserMoneyProject> check = AuthValidate.checkUser(user);
//          if (check != null) {
//              return check;
//          }
//          Response<UserMoneyProject> resp = new Response<>();
//          
//          //参数错误
//          if(model.getId()==null){
//              resp.setResultMsg(ErrorTag.PARAM_NULL.getMessage());
//              resp.setResultCode(ErrorTag.PARAM_NULL.getCode());
//              return resp;
//          }
//          UserMoneyProject modelDb = userMoneyProjectService.get(model.getId());
//          if(modelDb.getStatus()==null || modelDb.getStatus().intValue() != BuyProjectStatus.NORMAL.getValue()){
//              resp.setResultMsg(ErrorTag.USER_MONEY_PROJECT_CLOSE.getMessage());
//              resp.setResultCode(ErrorTag.USER_MONEY_PROJECT_CLOSE.getCode());
//              return resp;
//          }
//          if(!modelDb.getUserId().equals(user.getId())){
//              resp.setResultMsg(ErrorTag.USER_MONEY_PROJECT_NOT_USER.getMessage());
//              resp.setResultCode(ErrorTag.USER_MONEY_PROJECT_NOT_USER.getCode());
//              return resp;
//          }
//          
//          //项目错误
//          Project p = projectService.findById(modelDb.getProjectId());
//          if(p==null ||p.getStatus()==null || p.getStatus().intValue()!=ProjectStatus.PUBLISHED.getValue()){
//              resp.setResultMsg(ErrorTag.PROJECT_NOT_EXISTS.getMessage());
//              resp.setResultCode(ErrorTag.PROJECT_NOT_EXISTS.getCode());
//              return resp;
//          }
//          if(p.getStartTime().after(new Date())){
//              resp.setResultMsg(ErrorTag.PROJECT_NOT_START.getMessage());
//              resp.setResultCode(ErrorTag.PROJECT_NOT_START.getCode());
//              return resp;
//          }
//          if(p.getEndTime().before(new Date())){
//              resp.setResultMsg(ErrorTag.PROJECT_IS_END.getMessage());
//              resp.setResultCode(ErrorTag.PROJECT_IS_END.getCode());
//              return resp;
//          }
//          //币种错误
//          MoneyType mt = moneyTypeService.findById(modelDb.getTypeId());
//          if(mt==null){
//              resp.setResultMsg(ErrorTag.MONEY_TYPE_NOT_EXISTS.getMessage());
//              resp.setResultCode(ErrorTag.MONEY_TYPE_NOT_EXISTS.getCode());
//              return resp;
//          }
//         //项目币种不存在
//          ProjectMoney pm = projectMoneyService.findByProjectIdAndTypeId(modelDb.getProjectId(), modelDb.getTypeId());
//          if(pm==null){
//              resp.setResultMsg(ErrorTag.PROJECT_MONEY_NOT_EXISTS.getMessage());
//              resp.setResultCode(ErrorTag.PROJECT_MONEY_NOT_EXISTS.getCode());
//              return resp;
//          }
//          
//          //保存状态
//          modelDb.setStatus(BuyProjectStatus.NORMAL.getValue());
//          userMoneyProjectService.submit(modelDb);
//          
//          //返回剩余金额
//          userMoneyService.save(user.getId(), modelDb.getTypeId(), modelDb.getMoney(), MoneyLogType.BUY_PROJECT_NO, null);
//          
//          //保存项目币种已投资金额
//          pm.setMoney(pm.getMoney() - modelDb.getMoney());
//          projectMoneyService.save(pm);
//          
//          //保存项目的投资进度
//          projectService.saveProgress(p.getId());
//          
//          resp.setRows(model);
//          resp.setTotal(1);
//          return resp;
//      }
}
