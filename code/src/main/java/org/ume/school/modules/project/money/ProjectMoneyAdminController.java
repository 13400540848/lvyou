package org.ume.school.modules.project.money;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ume.school.modules.model.ErrorTag;
import org.ume.school.modules.model.entity.Project;
import org.ume.school.modules.model.entity.ProjectMoney;
import org.ume.school.modules.project.ProjectService;

import com.bluesimon.wbf.AdminLogined;
import com.bluesimon.wbf.IUser;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.utils.AuthValidate;

/**
 * Created by Zz on 2018/9/1.
 */
@RestController
@RequestMapping("/v0.1/admin/project/money")
public class ProjectMoneyAdminController {

    @Resource
    private ProjectService projectService;

    @Resource
    private ProjectMoneyService projectMoneyService;
    
    /**
     * 获取详情
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<ProjectMoney> getById(@PathVariable("id") String id) {
        ProjectMoney model = projectMoneyService.findById(id);
        return new Response<>(model);
    }

    /**
     * 项目币种配置数据
     *
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Response<List<ProjectMoney>> getListByProjectId(
            @RequestParam(value = "project_id", required = false) String projectId,
            @AdminLogined IUser user) {
        Response<List<ProjectMoney>> check = AuthValidate.checkAdmin(user);
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
        List<ProjectMoney> list = projectMoneyService.findByProjectId(projectId);
        return new Response<>(projectMoneyService.parse(list));
    }

    /**
    * 提交项目币种配置
    * @return
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Response<ProjectMoney> submitProject(@RequestBody ProjectMoney model, @AdminLogined IUser user) {
        Response<ProjectMoney> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        Project project = projectService.findById(model.getProjectId());
        if (project == null) {
            check = new Response<>();
            check.setResultCode(ErrorTag.PROJECT_NOT_EXISTS.getCode());
            check.setResultMsg(ErrorTag.PROJECT_NOT_EXISTS.getMessage());
            return check;
        }
        Response<ProjectMoney> resp = new Response<>();
        if (StringUtils.isEmpty(model.getId())) { //新增
            model.setId(UUID.randomUUID().toString());
            model.setMoney((double)0);
            resp.setRows(projectMoneyService.save(model));
        }
        else { // 修改
            ProjectMoney modelDb = projectMoneyService.findById(model.getId());
            if(modelDb==null){
                resp.setResultCode(ErrorTag.PROJECT_MONEY_NOT_EXISTS.getCode());
                resp.setResultMsg(ErrorTag.PROJECT_MONEY_NOT_EXISTS.getMessage());
            }
            else{
                model.setMoney(modelDb.getMoney());
                resp.setRows(projectMoneyService.save(model));
            }
        }
        return resp;
    }
    
    /**
     * 删除
     * @param project
     * @param user
     * @return
     */
     @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
     public Response<ProjectMoney> deleteProject(@PathVariable("id") String id, @AdminLogined IUser user) {
         Response<ProjectMoney> check = AuthValidate.checkAdmin(user);
         if (check != null) {
             return check;
         }
         Response<ProjectMoney> resp = new Response<>();
         if (!StringUtils.isEmpty(id)) {
             ProjectMoney modelDb = projectMoneyService.findById(id);
             if(modelDb==null){
                 resp.setResultCode(ErrorTag.PROJECT_MONEY_NOT_EXISTS.getCode());
                 resp.setResultMsg(ErrorTag.PROJECT_MONEY_NOT_EXISTS.getMessage());
             }
             else if(modelDb.getMoney() > 0){
                 resp.setResultCode(ErrorTag.PROJECT_HAS_MONEY.getCode());
                 resp.setResultMsg(ErrorTag.PROJECT_HAS_MONEY.getMessage());
             }
             else{
                 projectMoneyService.delete(id);
             }
         }
         else { 
             resp.setResultCode(ErrorTag.PARAM_NULL.getCode());
             resp.setResultMsg(ErrorTag.PARAM_NULL.getMessage());
         }
         return resp;
     }
}
