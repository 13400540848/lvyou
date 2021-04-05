package org.ume.school.modules.project;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ume.school.modules.model.ErrorTag;
import org.ume.school.modules.model.entity.Project;
import org.ume.school.modules.project.money.ProjectMoneyService;

import com.bluesimon.wbf.AdminLogined;
import com.bluesimon.wbf.IUser;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.utils.AuthValidate;

/**
 * Created by Zz on 2018/8/26.
 */
@RestController
@RequestMapping("/v0.1/admin/project")
public class ProjectAdminController {

    @Resource
    private ProjectService projectService;
    
    @Resource
    private ProjectMoneyService projectMoneyService;
    
    /**
     * 获取项目详情
     */
    @RequestMapping(value = "/{project_id}", method = RequestMethod.GET)
    public Response<Project> getProjectById(@PathVariable("project_id") String projectId, @AdminLogined IUser user) {
        Response<Project> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        Project project = projectService.findById(projectId);
        project = projectService.parse(project);
        project.setProjectMoneys(projectMoneyService.parse(project.getProjectMoneys()));
        return new Response<>(project);
    }

    /**
     * 全部数据
     *
     * @param offset
     * @param limit
     * @param orderby
     * @param direction
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Response<List<Project>> getAdminProjects(@RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "star", required = false) Integer star,
            @RequestParam(value = "recommend", required = false) Integer isRecommend,
            @RequestParam(value = "offset", required = true) String offset,
            @RequestParam(value = "limit", required = true) String limit, @AdminLogined IUser user) {
        Response<List<Project>> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        Response<List<Project>> result = new Response<List<Project>>();
        final Project query = new Project();
        query.setTitle(title);
        query.setStatus(status);
        query.setStar(star);
        query.setIsRecommend(isRecommend);
        Page<Project> projects = projectService.findAll(query, offset, limit);
        result.setTotal(projects.getTotalElements());
        List<Project> list = projects.getContent();
        if(list!=null && !list.isEmpty()){
            list = projectService.parse(projects.getContent());
            for(Project item : list){
                item.setProjectMoneys(projectMoneyService.parse(item.getProjectMoneys()));
            }
            result.setRows(list);
        }
        return result;
    }

    /**
    * 提交项目
    * @param project
    * @param user
    * @return
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Response<Project> submitProject(@RequestBody Project project, @AdminLogined IUser user) {
        Response<Project> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        Response<Project> resp = new Response<>();
        if (StringUtils.isEmpty(project.getId())) { //新增项目
            project.setId(UUID.randomUUID().toString());
            project.setCreateTime(new Date());
//            project.setAdminId(user.getId());
            project.setAdminName(user.getNickName());
//            project.setProgress((double)0);
            resp.setRows(projectService.submitProject(project));
        }
        else { // 修改项目
            Project projectDb = projectService.findById(project.getId());
            if(projectDb==null){
                resp.setResultCode(ErrorTag.PROJECT_NOT_EXISTS.getCode());
                resp.setResultMsg(ErrorTag.PROJECT_NOT_EXISTS.getMessage());
            }
            else{
//                project.setProgress(projectDb.getProgress());
                project.setUpdateTime(new Date());
//                project.setAdminId(user.getId());
                project.setAdminName(user.getNickName());
                resp.setRows(projectService.submitProject(project));
            }
        }
        return resp;
    }
    
    /**
     * 删除项目
     * @param project
     * @param user
     * @return
     */
     @RequestMapping(value = "/{project_id}", method = RequestMethod.DELETE)
     public Response<Project> deleteProject(@PathVariable("project_id") String projectId, @AdminLogined IUser user) {
         Response<Project> check = AuthValidate.checkAdmin(user);
         if (check != null) {
             return check;
         }
         Response<Project> resp = new Response<>();
         if (!StringUtils.isEmpty(projectId)) {
             Project projectDb = projectService.findById(projectId);
             if(projectDb==null){
                 resp.setResultCode(ErrorTag.PROJECT_NOT_EXISTS.getCode());
                 resp.setResultMsg(ErrorTag.PROJECT_NOT_EXISTS.getMessage());
             }
             else{
//                 List<ProjectMoney> listMoney = projectMoneyService.findByProjectId(projectId);
//                 if(!listMoney.isEmpty()){
//                     resp.setResultCode(ErrorTag.PROJECT_MONEY_EXISTS.getCode());
//                     resp.setResultMsg(ErrorTag.PROJECT_MONEY_EXISTS.getMessage());
//                 }
//                 else{
//                     projectService.deleteProject(projectDb);
//                 }
                 projectService.deleteProject(projectDb);
             }
         }
         else { 
             resp.setResultCode(ErrorTag.PARAM_NULL.getCode());
             resp.setResultMsg(ErrorTag.PARAM_NULL.getMessage());
         }
         return resp;
     }
}
