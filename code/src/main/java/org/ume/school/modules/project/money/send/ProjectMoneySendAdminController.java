package org.ume.school.modules.project.money.send;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ume.school.modules.model.ErrorTag;
import org.ume.school.modules.model.entity.Project;
import org.ume.school.modules.model.entity.ProjectMoneySend;
import org.ume.school.modules.model.entity.UserMoneyProject;
import org.ume.school.modules.model.enums.MoneyLogType;
import org.ume.school.modules.model.enums.UserMoneyProjectStatus;
import org.ume.school.modules.project.ProjectService;
import org.ume.school.modules.user.money.UserMoneyService;
import org.ume.school.modules.user.money.project.UserMoneyProjectService;

import com.bluesimon.wbf.AdminLogined;
import com.bluesimon.wbf.IUser;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.utils.AuthValidate;

/**
 * Created by Zz on 2018/10/14.
 */
@RestController
@RequestMapping("/v0.1/admin/project/money/send")
public class ProjectMoneySendAdminController {

    @Resource
    private ProjectService projectService;

    @Resource
    private ProjectMoneySendService projectMoneySendService;
    
    @Resource
    private UserMoneyProjectService userMoneyProjectService;
    
    @Resource
    private UserMoneyService userMoneyService;
    
    
    
    /**
     * 获取详情
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<ProjectMoneySend> getById(@PathVariable("id") String id) {
        ProjectMoneySend model = projectMoneySendService.findById(id);
        return new Response<>(model);
    }

    /**
     * 项目发币数据
     *
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Response<List<ProjectMoneySend>> getListByProjectId(
            @RequestParam(value = "project_id", required = false) String projectId,
            @AdminLogined IUser user) {
        Response<List<ProjectMoneySend>> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        Project project = projectService.findById(projectId);
        if (project == null) {
            check = new Response<>();
            check.setResultCode(ErrorTag.PROJECT_NOT_EXISTS.getCode());
            check.setResultMsg(ErrorTag.PROJECT_NOT_EXISTS.getMessage());
            return check;
        }
        List<ProjectMoneySend> list = projectMoneySendService.findByProjectId(projectId);
        return new Response<>(projectMoneySendService.parse(list));
    }

    /**
    * 发币
    * @return
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Response<ProjectMoneySend> submitProjectMoneySend(@RequestBody ProjectMoneySend model, @AdminLogined IUser user) {
        Response<ProjectMoneySend> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        Response<ProjectMoneySend> resp = new Response<>();
        if(model.getProjectId().isEmpty() || model.getAllMoney() <=0){
            resp.setResultCode(ErrorTag.PARAM_NULL.getCode());
            resp.setResultMsg(ErrorTag.PARAM_NULL.getMessage());
            return resp;
        }
        Project project = projectService.findById(model.getProjectId());
        if (project == null) {
            resp.setResultCode(ErrorTag.PROJECT_NOT_EXISTS.getCode());
            resp.setResultMsg(ErrorTag.PROJECT_NOT_EXISTS.getMessage());
            return resp;
        }
       
        List<UserMoneyProject> list = userMoneyProjectService.findByProjectIdAndStatusOrderByCreateTimeDESC(model.getProjectId(), UserMoneyProjectStatus.NORMAL.getValue());
        if(list==null || list.isEmpty()){
            resp.setResultCode(ErrorTag.USER_MONEY_PROJECT_SEND_FINISHED.getCode());
            resp.setResultMsg(ErrorTag.USER_MONEY_PROJECT_SEND_FINISHED.getMessage());
            return resp;
        }
        
        //新增发币记录
        model.setId(UUID.randomUUID().toString());
        model.setSendMoney((double)0);
        model.setCreateTime(new Date());
        projectMoneySendService.submit(model);
        
        //开始发币
        double allMoney = model.getAllMoney();
        double sendMoney = 0;
        for(UserMoneyProject ump : list){
            if(allMoney < ump.getProjectMoney()){ //资金发完
                break;
            }
            //更新用户投资项目
            ump.setSendId(model.getId());
            ump.setSendMoney(ump.getProjectMoney());
            ump.setStatus(UserMoneyProjectStatus.SEND_FINISHED.getValue());
            ump.setSendTime(new Date());
            userMoneyProjectService.submit(ump);
            //用户钱包和交易记录
            userMoneyService.save(ump.getUserId(), project.getMoneyTypeId(), ump.getProjectMoney(), MoneyLogType.SEND_PROJECT, null);
            
            allMoney = allMoney - ump.getProjectMoney();
            sendMoney = sendMoney + ump.getProjectMoney();
        }
        
        //更新发币记录
        model.setSendMoney(sendMoney);
        projectMoneySendService.submit(model);
        
        resp.setRows(model);
        return resp;
    }
}
